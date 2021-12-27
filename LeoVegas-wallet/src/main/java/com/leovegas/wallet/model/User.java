package com.leovegas.wallet.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="USERS")
public class User extends AbstractEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public interface BasicValidation{}
	

	@Column(name = "user_id", unique = true)
	@Size(min = 5,max = 25, groups = BasicValidation.class)
	@NotNull(groups = BasicValidation.class)
	private String userID;

	@Column(name = "first_name")
	@Size(min = 2,max = 20, groups = BasicValidation.class)
	@NotNull(groups = BasicValidation.class)
	private String fName;

	@Column(name = "last_name")
	@Size(min = 2,max = 30, groups = BasicValidation.class)
	private String lName;

	@Column(unique = true)
	@Size(min = 6,max = 40, groups = BasicValidation.class)
	@NotNull(groups = BasicValidation.class)
   // @JsonProperty( access =  JsonProperty.Access.WRITE_ONLY) // unit test doesn't work with this.
    private String email;

		

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_no", referencedColumnName = "cust_account_number")
	private Account account;
	  
	 
	public User(
			@Size(min = 5, max = 25, groups = BasicValidation.class) @NotNull(groups = BasicValidation.class) String userID,
			@Size(min = 2, max = 20, groups = BasicValidation.class) @NotNull(groups = BasicValidation.class) String fName,
			@Size(min = 2, max = 30, groups = BasicValidation.class) String lName,
			@Size(min = 6, max = 40, groups = BasicValidation.class) @NotNull(groups = BasicValidation.class) String email) {
		super();
		this.userID = userID;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
	}


	public User() {
		super();
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Account getAccount() { return account; }


	public void setAccount(Account account) { this.account = account; }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	 

	

}
