package com.biren.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ers_user_roles")
public class UserRoles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ers_user_role_id", unique = true, nullable = false)
	private int ersUserRoleId;
	
	@Column(name = "user_role", nullable = false)
	private String UserRole;
	
	public UserRoles() {
		super();
	}

	public UserRoles(String userRole) {
		super();
		UserRole = userRole;
	}

	public int getErsUserRoleId() {
		return ersUserRoleId;
	}

	public void setErsUserRoleId(int ersUserRoleId) {
		this.ersUserRoleId = ersUserRoleId;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((UserRole == null) ? 0 : UserRole.hashCode());
		result = prime * result + ersUserRoleId;
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
		UserRoles other = (UserRoles) obj;
		if (UserRole == null) {
			if (other.UserRole != null)
				return false;
		} else if (!UserRole.equals(other.UserRole))
			return false;
		if (ersUserRoleId != other.ersUserRoleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRoles [ersUserRoleId=" + ersUserRoleId + ", UserRole=" + UserRole + "]";
	}
	
	

}
