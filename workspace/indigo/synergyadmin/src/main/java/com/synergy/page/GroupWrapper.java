package com.synergy.page;

import java.io.Serializable;

public class GroupWrapper implements Serializable {

	private String name;

	private Boolean selected;

	private Long id;

	public GroupWrapper(Long id, String name, Boolean selected) {
		this.id = id;
		this.name = name;
		this.selected = selected;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
