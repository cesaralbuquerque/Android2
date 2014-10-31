package com.synergy.app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

import com.synergy.model.Company;
import com.synergy.model.MailMessage;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;
import com.synergy.service.CompanyService;
import com.synergy.service.MailMessageService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;
import com.synergy.service.VideoService;
import com.synergy.util.CipherUtil;
import com.synergy.util.SynergyConfig;

public class VMailServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache");
		WebApplicationContext springContext = (WebApplicationContext) req
				.getSession()
				.getServletContext()
				.getAttribute(
						WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		CompanyService companyService = (CompanyService) springContext
				.getBean("companyService");

		String view = req.getParameter("view");
		if (isNotNull(view)) {
			doView(req, resp, springContext, view);
			return;
		}

		String reply = req.getParameter("reply");
		if (isNotNull(reply)) {
			doReply(req, resp, springContext, reply);
			return;
		}

		String register = req.getParameter("register");
		if (isNotNull(register)) {
			doRegister(req, resp, springContext, register);
			return;
		}

		String chat = req.getParameter("chatInvite");
		if (isNotNull(chat)) {
			req.setAttribute("toUID", chat);
			req.getRequestDispatcher("/GuestChatUser.jsp").forward(req, resp);
			return;
		}

		String logout = req.getParameter("logout");
		if (isNotNull(logout)) {
			SessionUtil.destroySessionIdAndCookie(req, resp);
			req.getRequestDispatcher("/LoginSynergyChat.jsp")
					.forward(req, resp);
			return;
		}

		String activeAccount = req.getParameter("actAccount");
		if (activeAccount != null) {
			if (true == Boolean.valueOf(activeAccount)) {
				String clientId = req.getParameter("cid");
				try {
					clientId = CipherUtil.decrypt(clientId);
				} catch (Exception e) {
					throw new IllegalArgumentException(
							"ERROR: Account not found");
				}
				Company client = companyService.find(Long.parseLong(clientId));
				if (client == null) {
					throw new IllegalArgumentException(
							"ERROR: Account not found");
				}

				client.setConfirmStatus(Company.CONFIRMED);
				companyService.save(client);
				req.setAttribute("activatedEmail", client.getEmail());
				req.setAttribute("infoMessage",
						"Account activated with success.");
				req.getRequestDispatcher("/LoginSynergyChat.jsp").forward(req,
						resp);
				return;
			} else {
				throw new IllegalArgumentException(
						"ERROR: Invalid Parameteres.");
			}
		}

		req.setAttribute("canResize", "false");
		// The red5 server, should be stored in the Video table. This way we
		// can handle more than one Red5 server
		req.setAttribute("red5Server", SynergyConfig.instance()
				.getRed5RtmpServer());
		req.getRequestDispatcher("/SynergyChat.jsp").forward(req, resp);
	}

	private void doRegister(HttpServletRequest req, HttpServletResponse resp,
			WebApplicationContext springContext, String register)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		if (email.indexOf("@") == -1) {
			req.setAttribute("email", email);
			req.setAttribute("errorMessage", "Invalid Email.");
			req.getRequestDispatcher("/LoginSynergyChat.jsp?reply=" + register)
					.forward(req, resp);
			return;
		}
		SubscriberService subService = (SubscriberService) springContext
				.getBean("subscriberService");
		PurchaseService purchaseService = (PurchaseService) springContext
				.getBean("purchaseService");
		ProductService productService = (ProductService) springContext
				.getBean("productService");
		Subscriber subscriber = subService.getByEmail(email);
		if (subscriber != null && subscriber.getCompany() != null) {
			req.setAttribute("email", email);
			req.setAttribute("errorMessage",
					"Email already registered. Please enter password.");
			req.getRequestDispatcher("/LoginSynergyChat.jsp?reply=" + register)
					.forward(req, resp);
		} else {
			final String password = "123";
			String name = email.substring(0, email.indexOf("@"));
			subscriber = subService.createSubscriberAndCompany(name, email,
					password, "Company Name", "http://mywebsite.com");
			Product vmail = productService.getProductByName(Product.VMAIL);
			purchaseService.purchaseProduct(subscriber.getCompany(), vmail, 1,
					0, "1 Month Free", null);
			Product vchat = productService.getProductByName(Product.VCHAT);
			purchaseService.purchaseProduct(subscriber.getCompany(), vchat, 1,
					0, "1 Month Free", null);
			ArrayList<Product> products = new ArrayList<Product>();
			products.add(vmail);
			products.add(vchat);
			productService.updateProducts(subscriber, products);
			// subService.emailUserCreated(subscriber, password);
			SessionUtil.createSessionId(req, subscriber);
			req.getRequestDispatcher("/vmail?reply=" + register).forward(req,
					resp);
		}
	}

	private void doView(HttpServletRequest req, HttpServletResponse resp,
			WebApplicationContext springContext, String view)
			throws IOException, ServletException {
		String key = view;
		try {
			String[] dec = CipherUtil.decrypt(key).split(",");
			if (dec.length < 3) {
				throw new IllegalArgumentException(
						"Invalid URL: Video not found in our server.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR: Invalid Video Parameter");
		}
		req.setAttribute("encVideoId", key);
		req.setAttribute("red5Server", SynergyConfig.instance()
				.getRed5RtmpServer());
		req.getRequestDispatcher("/ViewVMail.jsp").forward(req, resp);
	}

	private void doView_old(HttpServletRequest req, HttpServletResponse resp,
			WebApplicationContext springContext, String view)
			throws IOException, ServletException {
		String key = view;// req.getParameter("vKey");
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
			e.printStackTrace();
			throw new RuntimeException("Error decrypting: " + e.getMessage());
		}

		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		// TODO - change this to be only check if the video exists, not need
		// to load the video
		Video v = videoService.getVideoByVideoId(videoId);
		if (v == null) {
			resp.getOutputStream().print(
					"Error: Video not found in our server.");
			return;
		}
		req.setAttribute("view", view);
		req.setAttribute("cid", cid);
		req.setAttribute("vId", videoId);
		req.setAttribute("uId", String.valueOf(v.getSubscriber().getId()));
		// The red5 server, should be stored in the Video table. This way we
		// can handle more than one Red5 server
		req.setAttribute("red5Server", SynergyConfig.instance()
				.getRed5RtmpServer());
		req.getRequestDispatcher("/ViewVMail.jsp").forward(req, resp);
	}

	private void doReply(HttpServletRequest req, HttpServletResponse resp,
			WebApplicationContext springContext, String reply)
			throws IOException, ServletException {
		String key = reply;// req.getParameter("vKey");
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
			e.printStackTrace();
			throw new RuntimeException("Error decrypting: " + e.getMessage());
		}

		VideoService videoService = (VideoService) springContext
				.getBean("videoService");
		Video v = videoService.getVideoByVideoId(videoId);
		if (v == null) {
			resp.getOutputStream().print(
					"Error: Original video not found in our server.");
			return;
		}
		String subject = "";
		if (sentMessageId != -1) {
			// TODO - optimize query to return just the subject of a
			// SentMessage
			MailMessageService mailService = (MailMessageService) springContext
					.getBean("messageService");
			MailMessage mail = mailService.find(sentMessageId);
			if (mail == null) {
				resp.getOutputStream().print(
						"Error: MailMessage not found in our server.");
			}
			subject = mail.getSubject();

		}

		req.setAttribute("replyEmail", v.getSubscriber().getEmail());
		req.setAttribute("subjectEmail", subject);
		req.setAttribute("canResize", "true");
		// The red5 server, should be stored in the Video table. This way we
		// can handle more than one Red5 server
		req.setAttribute("red5Server", SynergyConfig.instance()
				.getRed5RtmpServer());
		req.getRequestDispatcher("/SynergyChat.jsp").forward(req, resp);
	}

	private boolean isNotNull(String view) {
		return view != null && view.trim().length() > 0
				&& !view.trim().equals("null");
	}
}
