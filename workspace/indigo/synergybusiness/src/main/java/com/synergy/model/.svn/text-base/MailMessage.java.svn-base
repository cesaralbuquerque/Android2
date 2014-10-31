package com.synergy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MailMessage extends AbstractPersistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String subject;
	private String message;
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "Video_id")
	private Video video;

	@ManyToOne
	@JoinColumn(name = "Subscriber_id")
	private Subscriber owner;

	public Subscriber getOwner() {
		return owner;
	}

	public void setOwner(Subscriber subscriber) {
		this.owner = subscriber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String name) {
		this.subject = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String description) {
		this.message = description;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}