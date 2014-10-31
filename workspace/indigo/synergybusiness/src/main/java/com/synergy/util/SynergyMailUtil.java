package com.synergy.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SynergyMailUtil {

	public static SimpleEmail createSupportSimpleMail(String fromName, String subject, String content, String to, String toName) throws EmailException {
		SynergyConfig instance = SynergyConfig.instance();
		SimpleEmail email = new SimpleEmail();
		email.setTLS(true);
		email.setHostName(instance.getSupportMailHostName());
		email.setAuthentication(instance.getSupportMailUserName(), instance.getSupportMailPass());
		email.addTo(to, toName);
		email.setFrom(instance.getSupportMailFrom(), subject);
		email.setSubject(subject);
		email.setMsg(content);
		return email;
	}

	public static SimpleEmail createNoreplySimpleMail(String fromName, String subject, String content, String to, String toName) throws EmailException {
		SynergyConfig instance = SynergyConfig.instance();
		SimpleEmail email = new SimpleEmail();
		email.setTLS(true);
		email.setHostName(instance.getNoreplyMailHostName());
		email.setAuthentication(instance.getNoreplyMailUserName(), instance.getNoreplyMailPass());
		email.addTo(to, toName);
		email.setFrom(instance.getNoreplyMailFrom(), subject);
		email.setSubject(subject);
		email.setMsg(content);
		return email;
	}

}
