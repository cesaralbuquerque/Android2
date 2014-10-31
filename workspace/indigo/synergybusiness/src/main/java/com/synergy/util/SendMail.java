package com.synergy.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.smtp.SMTPTransport;

public class SendMail {

	public SendMail() {
	}

	public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
		final SynergyConfig instance = SynergyConfig.instance();
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", instance.getNoreplyMailHostName());
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

		t.setStartTLS(true);

		t.connect(instance.getNoreplyMailHostName(), instance.getNoreplyMailUserName(), instance.getNoreplyMailHostName());
		// t.sendMessage(msg, addressTo);
		t.sendMessage(msg, addressTo);
		// t.send(msg);
	}

	public void send(String SMTPhost, String From, String To, String Subject, String Body, String Attachment) throws SendMessageException, MessagingException {
		send(SMTPhost, null, From, To, Subject, Body, Attachment);
	}

	public void send(String SMTPhost, String replyTo, String From, String To, String Subject, String Body, String Attachment) throws SendMessageException, MessagingException {
		int StartPos = 0;
		int EndPos;
		char LastAttachChar;
		String Filename;
		String operation = null;
		final SynergyConfig instance = SynergyConfig.instance();
		// Check if attachment is null
		if (Attachment == null || Attachment.equals("")) {
			EndPos = 0;
		} else {
			EndPos = Attachment.length();
			LastAttachChar = Attachment.charAt(EndPos - 1);

			// If Attachment is terminated by comma, delete the last character
			// from attachment
			if (LastAttachChar == ',') {
				Attachment = Attachment.substring(StartPos, EndPos - 1);
				EndPos = Attachment.length();
			}
		}

		// Get properties and default session for the SMTP server
		Properties props = System.getProperties();
		props.put("mail.smtp.host", instance.getNoreplyMailHostName()/* SMTPhost */);
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(false);

		// Now try and create the mail message
		try {
			// Create a message
			Message msg = new MimeMessage(session);

			if (replyTo != null) {
				InternetAddress[] replyAddress = InternetAddress.parse(replyTo, false);
				msg.setReplyTo(replyAddress);
			}

			// Add sender to message
			operation = "FROM";
			msg.setFrom(new InternetAddress(From));

			// Add reciepient list to message
			operation = "TO";
			InternetAddress[] address = InternetAddress.parse(To, false);
			msg.setRecipients(Message.RecipientType.TO, address);

			// Add subject to message
			msg.setSubject(Subject);

			// Add date to message
			msg.setSentDate(new Date());

			// Add a new part to the message
			Multipart mp = new MimeMultipart();
			{
				MimeBodyPart mbp = new MimeBodyPart();
				// Add the html body to the new part
				mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(Body, "text/html")));
				mp.addBodyPart(mbp);
			}
			// Parse the attachment list and add files to the new part
			if (EndPos != 0) {
				// Get the first and last positions of the file separator
				int FilesepPos = Attachment.indexOf(",");
				int FilesepLastPos = Attachment.lastIndexOf(",");

				// The attachment contains only one file
				if (FilesepPos == -1) {
					FilesepPos = EndPos;
					EndPos = 0;
				}

				while (true) {
					MimeBodyPart mbp = new MimeBodyPart();
					Filename = Attachment.substring(StartPos, FilesepPos);
					FileDataSource fds = new FileDataSource(Filename);
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(fds.getName());
					mp.addBodyPart(mbp);
					if (EndPos == 0) {
						break;
					}
					Attachment = Attachment.substring(FilesepPos + 1, EndPos);
					EndPos = Attachment.length();
					if (FilesepPos == FilesepLastPos) {
						FilesepPos = EndPos;
						EndPos = 0;
					} else {
						FilesepPos = Attachment.indexOf(",");
						FilesepLastPos = Attachment.lastIndexOf(",");
					}
				}
			}

			// Add the new part to the message
			msg.setContent(mp);

			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

			t.setStartTLS(true);

			t.connect(instance.getNoreplyMailHostName(), instance.getNoreplyMailUserName(), instance.getNoreplyMailHostName());
			// t.sendMessage(msg, addressTo);
			t.sendMessage(msg, address);
		} catch (AddressException aex) {
			throw new SendMessageException("Email Address error " + operation + " value > " + aex.getMessage());
		} catch (MessagingException mex) {
			// Ignore the top level exception as this just tells us the main was
			// not sent
			// The next exception will give more detail
			Exception nested = mex.getNextException();
			if (nested == null) {
				throw new SendMessageException(mex.getMessage());
			}
			throw new SendMessageException(nested.getMessage());
		} catch (Exception ex) {
			throw new SendMessageException(ex.toString());
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			final SynergyConfig instance = SynergyConfig.instance();
			return new PasswordAuthentication(instance.getNoreplyMailUserName(), instance.getNoreplyMailPass());
		}
	}

}
