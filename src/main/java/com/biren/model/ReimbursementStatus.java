package com.biren.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ers_reimbursement_status")
public class ReimbursementStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reimb_status_id", unique = true, nullable = false)
	private int reimbStatusId;
	
	@Column(name = "reimb_status", nullable = false)
	private String reimbStatus;

	public ReimbursementStatus() {
		super();
	}

	public ReimbursementStatus(String reimbStatus) {
		super();
		this.reimbStatus = reimbStatus;
	}

	public int getReimbStatusId() {
		return reimbStatusId;
	}

	public void setReimbStatusId(int reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}

	public String getReimbStatus() {
		return reimbStatus;
	}

	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reimbStatus == null) ? 0 : reimbStatus.hashCode());
		result = prime * result + reimbStatusId;
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
		ReimbursementStatus other = (ReimbursementStatus) obj;
		if (reimbStatus == null) {
			if (other.reimbStatus != null)
				return false;
		} else if (!reimbStatus.equals(other.reimbStatus))
			return false;
		if (reimbStatusId != other.reimbStatusId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementStatus [reimbStatusId=" + reimbStatusId + ", reimbStatus=" + reimbStatus + "]";
	}
	
	

}
