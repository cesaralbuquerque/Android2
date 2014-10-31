package com.synergychat.dto;


public class InboxMailMessageDTO extends MailMessageDTO{

	private SubscriberDTO from;

	public void setFrom(SubscriberDTO from) {
		this.from = from;
	}

	public SubscriberDTO getFrom() {
		return from;
	}
	
}
