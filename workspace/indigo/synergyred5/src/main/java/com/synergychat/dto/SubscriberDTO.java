package com.synergychat.dto;

import java.io.Serializable;

import com.synergychat.Application;

public class SubscriberDTO implements Serializable {

	private Long id;

	private String name;

	private String email;

	private Byte presenceState = Application.OFFLINE;

	private Boolean canVMail = false;

	private Boolean canVChat = false;

	private Boolean admin = false;

	public SubscriberDTO() {
	}

	public SubscriberDTO(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCanVMail(Boolean canVMail) {
		this.canVMail = canVMail;
	}

	public Boolean getCanVMail() {
		return canVMail;
	}

	public void setCanVChat(boolean canVChat) {
		this.canVChat = canVChat;
	}

	public Boolean getCanVChat() {
		return canVChat;
	}

	public void setPresenceState(Byte presenceState) {
		this.presenceState = presenceState;
	}

	public Byte getPresenceState() {
		return presenceState;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getAdmin() {
		return admin;
	}

}
