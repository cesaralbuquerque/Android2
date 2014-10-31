package com.synergy.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( { 
	@NamedQuery(name = "ProductSubscriber.get", query = "from ProductSubscriber p where p.subscriber=?"),
	@NamedQuery(name = "ProductSubscriber.getProducts", query = "select products from ProductSubscriber p where p.subscriber=?"),
	@NamedQuery(name = "ProductSubscriber.getProductsCompany", query = "select products from ProductSubscriber p where p.subscriber.company=?")}
)
public class ProductSubscriber implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Subscriber_id")
	private Subscriber subscriber;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "ProductSubscriber_Product", joinColumns = { @JoinColumn(name = "Product_id") }, inverseJoinColumns = { @JoinColumn(name = "ProductSubscriber_id") })
	private Collection<Product> products = new HashSet<Product>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	public Collection<Product> getProducts() {
		return products;
	}

}
