package com.synergy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Company extends AbstractPersistent implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public static final Byte WAITING_CONFIRMATION = 0;
	public static final Byte CONFIRMED = 1;

	private Byte confirmStatus = 0;

	private String companyName;

	private String email;

	private String website;

	private String phone;
	
	private Date dateCreated;

	@OneToMany(mappedBy = "company")
	private Collection<Group> groups = new ArrayList<Group>();

	@OneToMany(mappedBy = "company")
	@OrderBy("id desc")
	private Collection<Purchase> purchases = new ArrayList<Purchase>();

	private String braintreeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWebsite() {
		return website;
	}

	public void setGroups(Collection<Group> groups) {
		this.groups = groups;
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Collection<Purchase> getPurchases() {
		return purchases;
	}

	public void setBraintreeId(String braintreeId) {
		this.braintreeId = braintreeId;
	}

	public String getBraintreeId() {
		return braintreeId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setConfirmStatus(Byte confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Byte getConfirmStatus() {
		return confirmStatus;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
}
