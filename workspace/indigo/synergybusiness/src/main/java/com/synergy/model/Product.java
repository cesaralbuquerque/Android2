package com.synergy.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( { @NamedQuery(name = "Product.getByName", query = "from Product u where u.name=?") })
public class Product extends AbstractPersistent {

	public static final String VMAIL = "VMail";
	public static final String VCHAT = "VChat";
	public static final String VMEET = "VMeet";
	public static final String VWEBCHAT = "VWebChat";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToMany(mappedBy = "products")
	@JoinColumn(name = "ProductSubscriber_id")
	private Collection<ProductSubscriber> subscribers = new ArrayList<ProductSubscriber>();

	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object other) {
		if (super.equals(other)) {
			return true;
		}

		if (other instanceof Product) {
			if (((Product) other).getId().equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setSubscribers(Collection<ProductSubscriber> subscribers) {
		this.subscribers = subscribers;
	}

	public Collection<ProductSubscriber> getSubscribers() {
		return subscribers;
	}
	
}
