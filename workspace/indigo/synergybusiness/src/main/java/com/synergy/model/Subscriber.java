package com.synergy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "User_id")
@NamedQueries( { @NamedQuery(name = "Subscriber.authenticate", query = "from Subscriber u where u.deleted is false and u.email=? and u.password=?"), @NamedQuery(name = "Subscriber.byEmail", query = "from Subscriber u where u.deleted is false and u.email=?") })
public class Subscriber extends User {

	@Lob
	private byte[] photo;

	private String email;
	
	private byte[] password;
	
	@OneToMany(mappedBy = "owner")
	@OrderBy("id desc")
	private Collection<MailMessage> mailMessages = new ArrayList<MailMessage>();

	@OneToMany(mappedBy = "owner")
	private Collection<MailContact> mailContacts = new ArrayList<MailContact>();

	public Subscriber() {
	}

	public Collection<MailMessage> getMailMessages() {
		return mailMessages;
	}

	public void setMailMessages(Collection<MailMessage> videos) {
		this.mailMessages = videos;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	private Boolean getBoolean(Boolean bool) {
		if (bool == null) {
			return Boolean.TRUE;
		} else {
			return bool;
		}
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	// public void setCompanies(Collection<SubscriberCompany> companies) {
	// this.companies = companies;
	// }
	//
	// public Collection<SubscriberCompany> getCompanies() {
	// return companies;
	// }

	public void setMailContacts(Collection<MailContact> mailContacts) {
		this.mailContacts = mailContacts;
	}

	public Collection<MailContact> getMailContacts() {
		return mailContacts;
	}

}
