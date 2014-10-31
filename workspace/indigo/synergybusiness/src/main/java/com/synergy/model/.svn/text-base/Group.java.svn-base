package com.synergy.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Groups")
@NamedQueries( { @NamedQuery(name = "Group.byNameAndCompany", query = "from Group g where g.deleted is false and g.name=? and g.company=?") })
public class Group implements Persistent {

	public static final byte PRIVATE = 0;
	public static final byte PUBLIC = 1;
	public static final byte INDIVIDUAL = 2;
	public static final byte ADMIN = 3;

	public static String PrivacyToString(byte status) {
		switch (status) {
		case PRIVATE:
			return "Private";
		case PUBLIC:
			return "Public";
		case INDIVIDUAL:
			return "Individual";
		case ADMIN:
			return "Admin";
		}
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private String email;

	private Byte privacy = PRIVATE;

	@ManyToOne
	@JoinColumn(name = "Client_id")
	private Company company;

	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "groups")
	private Collection<User> users = new ArrayList<User>();

	// IMPORTANT - one group can never be delete from the DB, because it's used
	// in the log.
	// when true, indicate that this group was removed.
	private Boolean deleted = Boolean.FALSE;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			if (obj instanceof Group) {
				if (this.id.equals(((Group) obj).getId())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public void setPrivacy(Byte privacy) {
		this.privacy = privacy;
	}

	public Byte getPrivacy() {
		return privacy;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
