package com.synergychat.dto;

import java.util.ArrayList;
import java.util.List;

import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;

import com.synergy.model.Group;
import com.synergy.model.InboxMailMessage;
import com.synergy.model.MailContact;
import com.synergy.model.MailMessage;
import com.synergy.model.SentMailMessage;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.model.Video;

public class DtoUtil {

	public static List<GroupDTO> convertGroup(List<Group> list) {
		ArrayList<GroupDTO> ret = new ArrayList<GroupDTO>();
		for (Group mailContact : list) {
			ret.add(DtoUtil.convertGroup(mailContact));
		}
		return ret;
	}

	public static GroupDTO convertGroup(Group group) {
		GroupDTO dto = new GroupDTO();
		dto.setName(group.getName());
		dto.setId(group.getId());
		dto.setPrivacy(group.getPrivacy());
		for (User s : group.getUsers()) {
			if (s instanceof Subscriber) {
				dto.getUsers().add(convertSubscriber((Subscriber) s));
			}
		}
		return dto;
	}

	public static List<MailContactDTO> convertMailContact(List<MailContact> list) {
		ArrayList<MailContactDTO> ret = new ArrayList<MailContactDTO>();
		for (MailContact mailContact : list) {
			ret.add(DtoUtil.convertMailContact(mailContact));
		}
		return ret;
	}

	public static SubscriberDTO convertSubscriber(Subscriber sub) {
		return convertSubscriber(sub, false, false, false);
	}

	public static SubscriberDTO convertSubscriber(Subscriber sub, boolean vmail, boolean vchat, boolean admin) {
		SubscriberDTO dto = new SubscriberDTO();
		if (sub == null) {
			dto.setName("");
			return dto;
		}
		dto.setId(sub.getId());
		dto.setEmail(sub.getEmail());
		dto.setName(sub.getName());
		dto.setCanVMail(vmail);
		dto.setCanVChat(vchat);
		dto.setAdmin(admin);
		return dto;
	}

	public static MailContactDTO convertMailContact(MailContact contact) {
		MailContactDTO dto = new MailContactDTO();
		dto.setName(contact.getName());
		dto.setEmail(contact.getEmail());
		return dto;
	}

	public static InboxMailMessageDTO convertInboxMail(InboxMailMessage mail) {
		InboxMailMessageDTO dto = new InboxMailMessageDTO();
		dto.setFrom(convertSubscriber(mail.getFrom()));
		updateMailMessageDTO(dto, mail);
		return dto;
	}

	public static SentMailMessageDTO convertSentMail(SentMailMessage mail) {
		SentMailMessageDTO dto = new SentMailMessageDTO();
		dto.setEmailsTo(mail.getEmailsTo());
		updateMailMessageDTO(dto, mail);
		return dto;
	}

	public static void updateMailMessageDTO(MailMessageDTO dto, MailMessage mail) {
		dto.setDateCreated(mail.getDateCreated());
		dto.setId(mail.getId());
		dto.setMessage(mail.getMessage());
		dto.setSubject(mail.getSubject());
		if (mail.getVideo() != null) {
			dto.setVideo(convertVideo(mail.getVideo()));
		}
	}

	public static VideoDTO convertVideo(Video video) {
		if (video == null || video.getDeleted()) {
			return null;
		}
		VideoDTO dtoVideo = new VideoDTO();
		dtoVideo.setDateCreated(video.getDateCreated());
		dtoVideo.setSize(video.getSize());
		dtoVideo.setVideoId(video.getVideoId());
		dtoVideo.setId(video.getId());
		dtoVideo.setName(video.getName());
		return dtoVideo;
	}
}
