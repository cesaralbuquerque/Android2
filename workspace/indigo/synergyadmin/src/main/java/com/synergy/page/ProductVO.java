package com.synergy.page;

import java.io.Serializable;

public class ProductVO implements Serializable {

	private Long id = null;
	private String name = null;
	private Float price = null;
	private SelectOption noOfUsers = null;

	public ProductVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public SelectOption getNoOfUsers() {
		return noOfUsers;
	}

	public void setNoOfUsers(SelectOption noOfUsers) {
		this.noOfUsers = noOfUsers;
	}

}