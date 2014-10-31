package com.synergychat.dto;

import java.util.ArrayList;
import java.util.List;

public class GroupDTO {

	private List<SubscriberDTO> users = new ArrayList<SubscriberDTO>();

	private Long id;

	private String name;

	private Byte privacy;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUsers(List<SubscriberDTO> users) {
		this.users = users;
	}

	public List<SubscriberDTO> getUsers() {
		return users;
	}

	public void setPrivacy(Byte privacy) {
		this.privacy = privacy;
	}

	public Byte getPrivacy() {
		return privacy;
	}

}
