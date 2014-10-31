package com.synergy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Chat implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String chatlog;

	private String title;

	private Date date;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "Chat_User", joinColumns = { @JoinColumn(name = "Chat_id") }, inverseJoinColumns = { @JoinColumn(name = "User_id") })
	private Collection<User> users = new ArrayList<User>();

	@ManyToOne
	@JoinColumn(name = "Client_id")
	private Company client;

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Company getClient() {
		return client;
	}

	public void setClient(Company client) {
		this.client = client;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChatlog() {
		return chatlog;
	}

	public void setChatlog(String log) {
		this.chatlog = log;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
