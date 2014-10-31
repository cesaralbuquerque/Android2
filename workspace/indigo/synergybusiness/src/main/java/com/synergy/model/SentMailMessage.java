package com.synergy.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "MailMessage_id")
@NamedQueries( { @NamedQuery(name = "SentMailMessage.getMessages", query = "from SentMailMessage u where u.owner=? order by dateCreated desc") })
public class SentMailMessage extends MailMessage {

	private String emailsTo;

	public void setEmailsTo(String emailsTo) {
		this.emailsTo = emailsTo;
	}

	public String getEmailsTo() {
		return emailsTo;
	}

}
