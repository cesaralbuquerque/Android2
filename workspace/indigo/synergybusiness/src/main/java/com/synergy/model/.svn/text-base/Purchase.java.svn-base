package com.synergy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( { @NamedQuery(name = "Purchase.getByProduct", query = "from Purchase p where p.company=? and p.product=? and p.status=1"), @NamedQuery(name = "Purchase.getByCompany", query = "from Purchase p where p.company=? and p.status=1") })
public class Purchase extends AbstractPersistent {

	public static final byte APPROVED = 1;
	public static final byte CANCELLED = 2;
	public static final byte DENIED = 3;

	public static String StatusToString(Byte status) {
		switch (status) {
		case APPROVED:
			return "APPROVED";
		case CANCELLED:
			return "CANCELLED";
		case DENIED:
			return "DENIED";//		
		}
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Byte status;

	private String description;

	private String subscriptionId;

	private Integer quantity;

	private Float priceTotal;

	private Date date;

	private Date dateCancelled;

	@ManyToOne
	@JoinColumn(name = "Company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "Product_id")
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setPriceTotal(Float priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Float getPriceTotal() {
		return priceTotal;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getStatus() {
		return status;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDateCancelled(Date dateCancelled) {
		this.dateCancelled = dateCancelled;
	}

	public Date getDateCancelled() {
		return dateCancelled;
	}

}
