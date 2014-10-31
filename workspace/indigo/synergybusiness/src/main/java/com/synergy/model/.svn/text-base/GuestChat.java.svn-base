package com.synergy.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "User_id")
@NamedQueries( { @NamedQuery(name = "GuestChat.fromCompany", query = "from GuestChat u where u.deleted is false and u.company=?") })
public class GuestChat extends User implements Persistent {

	private String email;

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
