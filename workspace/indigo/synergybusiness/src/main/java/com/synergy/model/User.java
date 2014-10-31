package com.synergy.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries( { @NamedQuery(name = "User.fromClient", query = "from User u where u.deleted is false and u.company=?") })
public class User extends AbstractPersistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "User_Group", joinColumns = { @JoinColumn(name = "User_id") }, inverseJoinColumns = { @JoinColumn(name = "Groups_id") })
	private Collection<Group> groups = new HashSet<Group>();

	@ManyToMany(mappedBy = "users")
	private Collection<Chat> chats = new HashSet<Chat>();

	private Date dateCreated;

	private String name;

	// IMPORTANT - one user can never be delete from the DB, because it's used
	// in the log.
	// when true, indicate that this group was removed.
	private Boolean deleted = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "Company_id")
	private Company company;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			Long otherId = ((User) obj).getId();
			if (this.id != null && otherId != null) {
				return this.id.equals(otherId);
			}
		}
		return super.equals(obj);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company client) {
		this.company = client;
	}

	public Collection<Chat> getChats() {
		return chats;
	}

	public void setChats(Collection<Chat> chats) {
		this.chats = chats;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setGroups(Collection<Group> groups) {
		this.groups = groups;
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
