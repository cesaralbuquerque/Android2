package com.synergy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "MailMessage_id")
@NamedQueries( { @NamedQuery(name = "InboxMailMessage.getMessages", query = "from InboxMailMessage u where u.owner=? order by dateCreated desc") })
public class InboxMailMessage extends MailMessage {

	@ManyToOne
	@JoinColumn(name = "SubscriberFrom_id")
	private Subscriber from;

	public void setFrom(Subscriber from) {
		this.from = from;
	}

	public Subscriber getFrom() {
		return from;
	}

}
