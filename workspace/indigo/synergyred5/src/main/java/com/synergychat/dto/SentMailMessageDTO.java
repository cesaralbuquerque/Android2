package com.synergychat.dto;

public class SentMailMessageDTO extends MailMessageDTO {

	private String emailsTo;

	public void setEmailsTo(String emailsTo) {
		this.emailsTo = emailsTo;
	}

	public String getEmailsTo() {
		return emailsTo;
	}
	
}
