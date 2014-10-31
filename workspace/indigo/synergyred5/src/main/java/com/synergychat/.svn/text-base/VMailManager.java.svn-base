package com.synergychat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;

import org.red5.io.amf3.ByteArray;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;
import com.synergy.service.MailMessageService;
import com.synergy.service.SubscriberService;
import com.synergy.service.VideoService;
import com.synergy.util.CipherUtil;
import com.synergy.util.SendMessageException;
import com.synergy.util.SynergyConfig;
import com.synergychat.dto.DtoUtil;
import com.synergychat.dto.InboxMailMessageDTO;
import com.synergychat.dto.SentMailMessageDTO;
import com.synergychat.dto.VideoDTO;
import com.synergychat.dto.ViewVideoDTO;

public class VMailManager {

	private static Logger log = Red5LoggerFactory.getLogger(VMailManager.class);

	private ClassLoader refClassLoader = Thread.currentThread()
			.getContextClassLoader();

	private ClassLoader origLoader = Thread.currentThread()
			.getContextClassLoader();

	private void toggleClassLoader() {
		if (refClassLoader == origLoader) {
			Thread.currentThread().setContextClassLoader(
					getClass().getClassLoader());
		} else {
			Thread.currentThread().setContextClassLoader(origLoader);
		}
	}

	protected EntityManagerFactory lookupEntityManagerFactory() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		return (EntityManagerFactory) wac.getBean("entityManagerFactory",
				EntityManagerFactory.class);
	}

	protected EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	public WebApplicationContext getSpringContext() {
		log.info("getSpringContext");
		ServletContext ctx = getServletContext();
		WebApplicationContext springContext = (WebApplicationContext) ctx
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return springContext;
	}

	public ServletContext getServletContext() {
		log.info("getServletContext");
		ApplicationContext appCtx = app.getContext().getApplicationContext();
		System.out.println("appCtx: " + appCtx);
		ServletContext ctx = ((XmlWebApplicationContext) appCtx)
				.getServletContext();
		System.out.println("ctx: " + ctx);
		return ctx;
	}

	public void closeDbContext(EntityManagerFactory emf) {
		log.info("closeDBContext: " + emf);
		toggleClassLoader();
		if (!participate) {
			log.debug("CLOSE DB CONTEXT");
			EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
					.unbindResource(emf);
			emHolder.getEntityManager().close();
		}
	}

	public EntityManagerFactory startDbContext() {
		log.info("startDbContext");
		toggleClassLoader();
		EntityManagerFactory emf;
		emf = lookupEntityManagerFactory();
		log.debug("START DB CONTEXT");
		participate = false;
		if (TransactionSynchronizationManager.hasResource(emf)) {
			log.debug("START DB CONTEXT-PARTICIPATE=TRUE");
			participate = true;
		} else {
			try {
				EntityManager em = createEntityManager(emf);
				TransactionSynchronizationManager.bindResource(emf,
						new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				log.error(ex.getMessage(), ex);
				throw new DataAccessResourceFailureException(
						"Could not create JPA EntityManager", ex);
			}
		}
		return emf;
	}

	boolean participate;

	// Application components
	private Application app;

	private ArrayList<? extends Object> filter(
			Collection<? extends Object> list, int start, int end) {
		ArrayList<Object> ret = new ArrayList<Object>();
		Iterator<Object> iter = (Iterator<Object>) list.iterator();
		for (int i = 0; iter.hasNext() && i < end; i++) {
			Object cur = iter.next();
			if (i >= start) {
				ret.add(cur);
			}
		}
		return ret;
	}

	public Object getInboxMailMessages(int start, int end) {
		log.info("getInboxMailMessages");
		IConnection conn = Red5.getConnectionLocal();

		WebApplicationContext springContext = getSpringContext();

		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		MailMessageService messageService = (MailMessageService) springContext
				.getBean("messageService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Subscriber user = subscriberService.find(Long.parseLong(fromUID));
			log.info("messageService.getInboxMailMessage: " + user);
			final List<InboxMailMessage> inboxMessages = messageService
					.getInboxMailMessage(user);

			ArrayList<InboxMailMessageDTO> r = new ArrayList<InboxMailMessageDTO>();
			if (user != null) {
				ArrayList<InboxMailMessage> filtered = (ArrayList<InboxMailMessage>) filter(
						inboxMessages, start, end);
				for (InboxMailMessage mail : filtered) {
					r.add(DtoUtil.convertInboxMail(mail));
				}
			}
			Collections.sort(r, new Comparator<InboxMailMessageDTO>() {

				public int compare(InboxMailMessageDTO o1,
						InboxMailMessageDTO o2) {
					return o2.getId().compareTo(o1.getId());
				}

			});
			return r;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public ViewVideoDTO getViewVideoInfo(String encVideoId) {
		log.info("getViewVideoInfo: " + encVideoId);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			ViewVideoDTO ret = new ViewVideoDTO();
			String key = encVideoId;
			String cid = null;
			String videoId = null;
			Long sentMessageId = (long) -1;
			try {
				String[] dec = CipherUtil.decrypt(key).split(",");
				if (dec.length < 3) {
					throw new IllegalArgumentException(
							"Invalid URL: Video not found in our server.");
				}
				cid = dec[0];
				videoId = dec[1];
				sentMessageId = Long.parseLong(dec[2]);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				throw new RuntimeException("Error decrypting: "
						+ e.getMessage());
			}

			VideoService videoService = (VideoService) springContext
					.getBean("videoService");
			log.info("getVideoByVideoId: " + videoId);
			Video v = videoService.getVideoByVideoId(videoId);
			if (v == null) {
				throw new RuntimeException(
						"Error: Original video not found in our server.");
			}
			String subject = "";
			if (sentMessageId != -1) {
				// TODO - optimize query to return just the subject of a
				// SentMessage
				MailMessageService mailService = (MailMessageService) springContext
						.getBean("messageService");
				log.info("mailService.find: " + sentMessageId);
				MailMessage mail = mailService.find(sentMessageId);
				if (mail == null) {
					throw new RuntimeException(
							"Error: MailMessage not found in our server.");
				}
				subject = mail.getSubject();

			}
			ret.setCid(String.valueOf(cid));
			ret.setEmail(v.getSubscriber().getEmail());
			ret.setOwnerId(String.valueOf(v.getSubscriber().getId()));
			ret.setSubject(subject);
			ret.setVideoId(videoId);
			return ret;
		} finally {
			closeDbContext(emf);
		}
	}

	public void addVideoView(String videoId) {
		WebApplicationContext springContext = getSpringContext();

		EntityManagerFactory emf = startDbContext();
		try {
			VideoService videoService = (VideoService) springContext
					.getBean("videoService");
			log.info("getVideoByVideoId: " + videoId);
			Video v = videoService.getVideoByVideoId(videoId);
			if (v == null) {
				throw new RuntimeException(
						"Error: Original video not found in our server.");
			}
			Integer viewCount = v.getViewCount() == null ? 0 : v.getViewCount();
			v.setViewCount(++viewCount);
			videoService.save(v);
		} finally {
			closeDbContext(emf);
		}
	}

	public Object getSentMailMessages(int start, int end) {
		log.info("getSentMailMessage");
		IConnection conn = Red5.getConnectionLocal();

		WebApplicationContext springContext = getSpringContext();

		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		MailMessageService messageService = (MailMessageService) springContext
				.getBean("messageService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Subscriber user = subscriberService.find(Long.parseLong(fromUID));
			log.info("messageService.getSentMailMessage: " + user);
			final List<SentMailMessage> inboxMessages = messageService
					.getSentMailMessage(user);

			ArrayList<SentMailMessageDTO> r = new ArrayList<SentMailMessageDTO>();
			if (user != null) {
				ArrayList<SentMailMessage> filtered = (ArrayList<SentMailMessage>) filter(
						inboxMessages, start, end);
				for (SentMailMessage mail : filtered) {
					r.add(DtoUtil.convertSentMail(mail));
				}
			}
			Collections.sort(r, new Comparator<SentMailMessageDTO>() {

				public int compare(SentMailMessageDTO o1, SentMailMessageDTO o2) {
					return o2.getId().compareTo(o1.getId());
				}

			});
			return r;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public Object getVideos(int start, int end) {
		log.info("getVideos");
		IConnection conn = Red5.getConnectionLocal();

		WebApplicationContext springContext = getSpringContext();

		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		VideoService messageService = (VideoService) springContext
				.getBean("videoService");

		String fromUID = (String) conn.getClient().getAttribute("uid");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Subscriber user = subscriberService.find(Long.parseLong(fromUID));
			log.info("messageService.getVideos: " + user);
			final List<Video> videos = messageService.getVideos(user);

			ArrayList<VideoDTO> r = new ArrayList<VideoDTO>();
			if (user != null) {
				List<Video> filtered = (List<Video>) filter(videos, start, end);
				for (Video mail : filtered) {
					final VideoDTO convertVideo = DtoUtil.convertVideo(mail);
					if (convertVideo != null) {
						r.add(convertVideo);
					}
				}
			}
			Collections.sort(r, new Comparator<VideoDTO>() {

				public int compare(VideoDTO o1, VideoDTO o2) {
					log.info("compare: " + o1 + ", " + o2);
					return o2.getId().compareTo(o1.getId());
				}

			});
			return r;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	/**
	 * Start recording the publishing stream for the specified
	 */
	public String recordVideo() {
		log.info("recordVideo");
		IConnection conn = Red5.getConnectionLocal();
		log.debug("Recording show for: " + conn.getScope().getContextPath());
		String fromUID = (String) conn.getClient().getAttribute("uid");
		if (fromUID == null) {
			throw new RuntimeException("Invalid user for video recording.");
		}
		String streamName = (String) conn.getClient()
				.getAttribute("streamName");
		if (streamName == null) {
			streamName = String.valueOf(fromUID + "_"
					+ System.currentTimeMillis());
		}
		// Get a reference to the current broadcast stream.
		ClientBroadcastStream stream = (ClientBroadcastStream) app
				.getBroadcastStream(conn.getScope(), fromUID);
		try {
			// Save the stream to disk.
			stream.saveAs(streamName, false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		conn.getClient().setAttribute("streamName", streamName);
		return streamName;
	}

	public String createVideoId() {
		log.info("createVideoId");
		IConnection conn = Red5.getConnectionLocal();
		String fromUID = (String) conn.getClient().getAttribute("uid");
		final String videoId = String.valueOf(fromUID + "_"
				+ System.currentTimeMillis());
		log.info("videoIdCreated: " + videoId);
		return videoId;
	}

	public String saveVideo(String videoId) {
		log.info("saveVideo: " + videoId);
		IConnection conn = Red5.getConnectionLocal();
		log.debug("Stop recording show for: "
				+ conn.getScope().getContextPath());
		String fromUID = (String) conn.getClient().getAttribute("uid");
		WebApplicationContext springContext = getSpringContext();
		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Subscriber subscriber = subscriberService.find(Long
					.parseLong(fromUID));
			if (subscriber == null) {
				throw new RuntimeException("User is not logged in.");
			}
			Video video = null;
			try {
				log.info("getVideoByVideoId: " + videoId);
				video = videoService.getVideoByVideoId(videoId);
			} catch (NoResultException e) {
				// just ignore
			}
			// check if already have one video recorded with this id
			if (video == null) {
				byte[] videoFrame = null;
				// byte[] videoFrame = convertToByteArray(snapshot);
				// if (videoFrame == null) {
				// videoFrame = new byte[] {};
				// }
				video = videoService.createVideo(subscriber, (long) 0,
						(long) 0/* stream.getBytesReceived() */, videoId,
						videoFrame);
			} else {
				if (!subscriber.equals(video.getSubscriber())) {
					throw new RuntimeException(
							"User is not owner of the current video.");
				}
			}

			return video.getId().toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

			closeDbContext(emf);
		}
	}

	public void cancelVideo(String videoId) {
		log.info("cancelVideo: " + videoId);
		EntityManagerFactory emf = startDbContext();
		try {
			if (videoId == null) {
				throw new RuntimeException(
						"User doesn't have one current video to cancel.");
			}
			WebApplicationContext springContext = getSpringContext();
			VideoService videoService = (VideoService) springContext
					.getBean("videoService");
			Video video = videoService.getVideoByVideoId(videoId);
			// check if already have one video recorded with this id
			if (video != null) {
				videoService.delete(video);
				// TODO - need to do coding to remove the FLV video file.
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public String stopRecordingVideo(ByteArray snapshot) {
		log.info("stopRecordingVideo: " + snapshot);
		IConnection conn = Red5.getConnectionLocal();
		log.debug("Stop recording show for: "
				+ conn.getScope().getContextPath());
		String fromUID = (String) conn.getClient().getAttribute("uid");
		// Get a reference to the current broadcast stream.
		ClientBroadcastStream stream = (ClientBroadcastStream) app
				.getBroadcastStream(conn.getScope(), fromUID);
		// Stop recording.
		stream.stopRecording();
		stream.stop();

		final String videoId = ((String) conn.getClient().getAttribute(
				"streamName")).trim();
		if (videoId == null) {
			throw new RuntimeException(
					"User doesn't have a current video to save.");
		}
		WebApplicationContext springContext = getSpringContext();
		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Subscriber subscriber = subscriberService.find(Long
					.parseLong(fromUID));
			if (subscriber == null) {
				throw new RuntimeException("User is not logged in.");
			}
			Video video = null;
			try {
				log.info("getVideoByVideoId: " + videoId);
				video = videoService.getVideoByVideoId(videoId);
			} catch (NoResultException e) {
				log.error(e.getMessage(), e);
				// just ignore
			}
			// check if already have one video recorded with this id
			if (video == null) {
				byte[] videoFrame = convertToByteArray(snapshot);
				if (videoFrame == null) {
					videoFrame = new byte[] {};
				}
				video = videoService.createVideo(subscriber, (long) 0,
						(long) stream.getBytesReceived(), videoId, videoFrame);
			} else {
				if (!subscriber.equals(video.getSubscriber())) {
					throw new RuntimeException(
							"User is not owner of the current video.");
				}
			}

			// try {
			// Process processo = Runtime.getRuntime().exec(
			// "ffmpeg -i /home/brian/redtest/dist/webapps/wab2/streams/5/" +
			// videoId
			// +
			// ".flv -s 176×144 -vcodec h263 -r 25 -b 200 -ab 64 -acodec libfaac -ac 1 -ar 8000 /home/brian/synergyupload/"
			// + videoId + ".3gp");
			// String line = "";
			// BufferedReader input = new BufferedReader(new
			// InputStreamReader(processo.getInputStream()));
			// while ((line = input.readLine()) != null) {
			// System.out.println(line);
			// }
			// input.close();
			// } catch (IOException e) {
			// log.error("Error converting video: " + videoId, e);
			// }
			return video.getId().toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

			closeDbContext(emf);
		}
	}

	public byte[] convertToByteArray(ByteArray _RAWBitmapImage) {
		log.info("convertToByteArray: ");
		if (_RAWBitmapImage == null) {
			return null;
		}
		// Use functionality in org.red5.io.amf3.ByteArray to get parameters of
		// the ByteArray
		int BCurrentlyAvailable = _RAWBitmapImage.bytesAvailable();
		int BWholeSize = _RAWBitmapImage.length(); // Put the Red5 ByteArray
		// into a standard Java
		// array of bytes
		byte c[] = new byte[BWholeSize];
		_RAWBitmapImage.readBytes(c);
		// Transform the byte array into a java buffered image
		// ByteArrayInputStream db = new ByteArrayInputStream(c);
		if (BCurrentlyAvailable > 0) {
			return c;
		}
		// System.out.println("The byte Array currently has " +
		// BCurrentlyAvailable + " bytes. The Buffer has " + db.available());
		return null;
		// try {
		// BufferedImage JavaImage = ImageIO.read(db);
		// // Now lets try and write the buffered image out to a file
		// if (JavaImage != null) {
		// // If you sent a jpeg to the server, just change PNG to JPEG and
		// Red5ScreenShot.png to .jpeg
		// ImageIO.write(JavaImage, "PNG", new File("Red5ScreenShot.png"));
		// }
		// } catch (IOException e) {
		// log.info("Save_ScreenShot: Writing of screenshot failed " + e);
		// System.out.println("IO		Error " + e);
		// }
		// }
		// return null;
	}

	public void cancelRecordingVideo() {
		log.info("cancelRecordingVideo");
		IConnection conn = Red5.getConnectionLocal();
		String fromUID = (String) conn.getClient().getAttribute("uid");
		final String videoId = ((String) conn.getClient().getAttribute(
				"streamName")).trim();
		EntityManagerFactory emf = startDbContext();
		try {
			ClientBroadcastStream stream = (ClientBroadcastStream) app
					.getBroadcastStream(conn.getScope(), fromUID);
			if (stream != null && stream.isRecording()) {
				// if still recording, just stop and remove the flv file
				stream.stopRecording();
				stream.stop();
				deleteFlvFile(videoId);
				return;
			}
			if (videoId == null) {
				throw new RuntimeException(
						"User doesn't have one current video to cancel.");
			}
			WebApplicationContext springContext = getSpringContext();
			VideoService videoService = (VideoService) springContext
					.getBean("videoService");
			Video video = videoService.getVideoByVideoId(videoId);
			// check if already have one video recorded with this id
			if (video != null) {
				videoService.delete(video);
				deleteFlvFile(video.getVideoId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public void removeVideo(String ids) {
		log.info("removeVideo" + ids);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();
		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		String fromUID = (String) conn.getClient().getAttribute("uid");
		EntityManagerFactory emf = startDbContext();
		try {
			String[] sp = ids.split(";");
			for (int i = 0; i < sp.length; i++) {
				String id = sp[i];
				Video video = videoService.find(Long.parseLong(id));

				videoService.delete(video);
				// if owner of the video, then delete the video file
				if (video.getSubscriber().getId().equals(
						Long.parseLong(fromUID))) {
					deleteFlvFile(video.getVideoId());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	public void removeVideoMessage(String ids) {
		log.info("removeVideoMessage: " + ids);
		IConnection conn = Red5.getConnectionLocal();
		WebApplicationContext springContext = getSpringContext();
		MailMessageService videoService = (MailMessageService) springContext
				.getBean("messageService");
		String fromUID = (String) conn.getClient().getAttribute("uid");
		EntityManagerFactory emf = startDbContext();
		try {
			String[] sp = ids.split(";");
			for (int i = 0; i < sp.length; i++) {
				String id = sp[i];
				log.info("videoService.find: " + id);
				MailMessage mail = videoService.find(Long.parseLong(id));
				final Video video = mail.getVideo();
				videoService.delete(mail);
				if (video != null) {
					// if owner of the video, then delete the video file
					if (video.getSubscriber().getId().equals(
							Long.parseLong(fromUID))) {
						deleteFlvFile(video.getVideoId());
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			closeDbContext(emf);
		}
	}

	private void deleteFlvFile(String videoId) throws IOException {
		log.info("deleteFlvFile: " + videoId);
		IConnection conn = Red5.getConnectionLocal();
		String clientId = (String) conn.getClient().getAttribute(
				Application.CLIENT_ID);
		final Resource videoFile = conn.getScope().getContext().getResource(
				"streams\\" + clientId + "\\" + videoId + ".flv");
		if (videoFile != null && videoFile.getFile().exists()) {
			if (videoFile.getFile().delete()) {
				log.info("Video file removed from the file system: " + clientId
						+ "/" + videoId);
			} else {
				log
						.warn("Video file couldn't be removed from the file system: "
								+ clientId + "/" + videoId);
			}
		} else {
			log.warn("Video file wasn't found in the file system: " + clientId
					+ "/" + videoId);
		}
	}

	public void sendVideoMessage(String videoId, String email_to,
			String subject, String message) throws MessagingException {
		log.info("sendVideoMessage: " + videoId + ", " + email_to + ", "
				+ subject + ", " + message);
		IConnection conn = Red5.getConnectionLocal();
		// TODO - check if videoId is the same as streamName.
		conn.getClient().setAttribute("streamName", null);
		String fromUID = (String) conn.getClient().getAttribute("uid");
		WebApplicationContext springContext = getSpringContext();
		SubscriberService subscriberService = (SubscriberService) springContext
				.getBean("subscriberService");
		MailMessageService messageService = (MailMessageService) springContext
				.getBean("messageService");
		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		EntityManagerFactory emf = startDbContext();
		try {
			log.info("subscriberService.find: " + fromUID);
			Video video = null;
			Subscriber subscriber = subscriberService.find(Long
					.parseLong(fromUID));
			if (videoId != null && videoId.trim().length() > 0) {
				video = videoService.getVideoByVideoId(videoId);
			}
			email_to = email_to.replaceAll(";", ",");
			InternetAddress[] addrs = InternetAddress.parse(email_to);

			SentMailMessage sent = messageService.sendVideoMessage(subscriber,
					addrs, subject, message, video);
			String company = subscriber.getCompany() != null ? subscriber
					.getCompany().getCompanyName() : subscriber.getName();

			String email = null;
			if (video != null) {
				String link = getVideoUrlLink(videoId, sent.getId());
				email = HTML_PAGES.getVideoMessageEmail(subscriber.getName(),
						company, subject, message, link);
			} else {
				email = HTML_PAGES.getTextMessageEmail(subscriber.getName(),
						company, subject, message);
			}
			com.synergy.util.SendMail sm = new com.synergy.util.SendMail();
			try {
				sm.send(null, subscriber.getEmail(), subscriber.getName()
						+ " <noreply@synergychat.com>", email_to, subject,
						email, null);
			} catch (SendMessageException e) {
				log.error(e.getMessage(), e);
				System.out.println("Error stack trace:" + e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} finally {
			closeDbContext(emf);
		}
	}

	public String getVideoUrlLink(String videoId) {
		log.info("getVideoUrlLink: " + videoId);
		return getVideoUrlLink(videoId, (long) -1);
	}

	public String getVideoUrlLink(String videoId, Long messageId) {
		log.info("getVideoUrlLink: " + videoId + ", " + messageId);
		IConnection conn = Red5.getConnectionLocal();
		String clientId = (String) conn.getClient().getAttribute(
				Application.CLIENT_ID);
		try {
			if (messageId == null) {
				messageId = (long) -1;
			}
			String vid = CipherUtil.encrypt(clientId + "," + videoId + ","
					+ messageId);
			// return SynergyConfig.instance().getAdminSectionHost() +
			// "/ui?view=" + vid;
			if (SynergyConfig.instance().isTestMode()) {
				return SynergyConfig.instance().getAdminSectionHost()
						+ "ui?view=" + vid;
			} else {
				return "http://www.synergychat.com/vmail?view=" + vid;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("Error encrypting: " + e.getMessage());
		}
	}

	/* ----- Spring injected dependencies ----- */

	public void setApplication(Application app) {
		log.info("Application: " + app);
		this.app = app;
	}
}
