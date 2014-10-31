package com.synergychat.dto;

import java.io.Serializable;
import java.util.Date;

public class MailMessageDTO implements Serializable {

	private Long id;

	private String subject;

	private String message;

	private Date dateCreated;

	private VideoDTO video;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public VideoDTO getVideo() {
		return video;
	}

	public void setVideo(VideoDTO video) {
		this.video = video;
	}

}
