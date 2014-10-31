package com.synergy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( { @NamedQuery(name = "MailContact.byEmail", query = "from MailContact u where u.owner=? and u.email=?"), 
	@NamedQuery(name = "MailContact.searchNameEmail", query = "from MailContact u where u.owner=? and (u.email like '%?%' or u.name like '%?%')"),
	@NamedQuery(name = "MailContact.getAll", query = "from MailContact u where u.owner=?")})
public class MailContact implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String email;

	@ManyToOne
	@JoinColumn(name = "Subscriber_id")
	private Subscriber owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOwner(Subscriber owner) {
		this.owner = owner;
	}

	public Subscriber getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
