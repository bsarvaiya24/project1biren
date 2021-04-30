package com.biren.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ers_reimbursement")
public class Reimbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reimb_id", unique = true, nullable = false)
	private int reimbId;
	
	@Column(name = "reimb_amount", nullable = false)
	private double reimbAmount;
	
	
	@Column(name = "reimb_submitted", nullable = false)
	private Date reimbSubmitted;
	
	
	@Column(name = "reimb_resolved")
	private Date reimbResolved;
	
	@Column(name = "reimb_description")
	private String reimbDescription;
	
	//TODO
	@Column(name = "reimb_receipt")
	private String reimbReceipt;
	
	@ManyToOne
	@JoinColumn(name = "ers_user_id_author", nullable = false) 
	private User reimbAuthor;
	
	@ManyToOne
	@JoinColumn(name = "ers_user_id_resolver") 
	private User reimbResolver;
	
	@ManyToOne
	@JoinColumn(name = "reimb_status_id", nullable = false) 
	private ReimbursementStatus reimbStatusId;
	
	@ManyToOne
	@JoinColumn(name = "reimb_type_id", nullable = false) 
	private ReimbursementType reimbTypeId;

	public Reimbursement() {
		super();
	}

	public Reimbursement(double reimbAmount, Date reimbSubmitted, User reimbAuthor, ReimbursementStatus reimbStatusId,
			ReimbursementType reimbTypeId) {
		super();
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public Date getReimbSubmitted() {
		return reimbSubmitted;
	}

	public void setReimbSubmitted(Date reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}

	public Date getReimbResolved() {
		return reimbResolved;
	}

	public void setReimbResolved(Date reimbResolved) {
		this.reimbResolved = reimbResolved;
	}

	public String getReimbDescription() {
		return reimbDescription;
	}

	public void setReimbDescription(String reimbDescription) {
		this.reimbDescription = reimbDescription;
	}

	public String getReimbReceipt() {
		return reimbReceipt;
	}

	public void setReimbReceipt(String reimbReceipt) {
		this.reimbReceipt = reimbReceipt;
	}

	public User getReimbAuthor() {
		return reimbAuthor;
	}

	public void setReimbAuthor(User reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}

	public User getReimbResolver() {
		return reimbResolver;
	}

	public void setReimbResolver(User reimbResolver) {
		this.reimbResolver = reimbResolver;
	}

	public ReimbursementStatus getReimbStatusId() {
		return reimbStatusId;
	}

	public void setReimbStatusId(ReimbursementStatus reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}

	public ReimbursementType getReimbTypeId() {
		return reimbTypeId;
	}

	public void setReimbTypeId(ReimbursementType reimbTypeId) {
		this.reimbTypeId = reimbTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(reimbAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reimbAuthor == null) ? 0 : reimbAuthor.hashCode());
		result = prime * result + ((reimbDescription == null) ? 0 : reimbDescription.hashCode());
		result = prime * result + reimbId;
		result = prime * result + ((reimbReceipt == null) ? 0 : reimbReceipt.hashCode());
		result = prime * result + ((reimbResolved == null) ? 0 : reimbResolved.hashCode());
		result = prime * result + ((reimbResolver == null) ? 0 : reimbResolver.hashCode());
		result = prime * result + ((reimbStatusId == null) ? 0 : reimbStatusId.hashCode());
		result = prime * result + ((reimbSubmitted == null) ? 0 : reimbSubmitted.hashCode());
		result = prime * result + ((reimbTypeId == null) ? 0 : reimbTypeId.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(reimbAmount) != Double.doubleToLongBits(other.reimbAmount))
			return false;
		if (reimbAuthor == null) {
			if (other.reimbAuthor != null)
				return false;
		} else if (!reimbAuthor.equals(other.reimbAuthor))
			return false;
		if (reimbDescription == null) {
			if (other.reimbDescription != null)
				return false;
		} else if (!reimbDescription.equals(other.reimbDescription))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (reimbReceipt == null) {
			if (other.reimbReceipt != null)
				return false;
		} else if (!reimbReceipt.equals(other.reimbReceipt))
			return false;
		if (reimbResolved == null) {
			if (other.reimbResolved != null)
				return false;
		} else if (!reimbResolved.equals(other.reimbResolved))
			return false;
		if (reimbResolver == null) {
			if (other.reimbResolver != null)
				return false;
		} else if (!reimbResolver.equals(other.reimbResolver))
			return false;
		if (reimbStatusId == null) {
			if (other.reimbStatusId != null)
				return false;
		} else if (!reimbStatusId.equals(other.reimbStatusId))
			return false;
		if (reimbSubmitted == null) {
			if (other.reimbSubmitted != null)
				return false;
		} else if (!reimbSubmitted.equals(other.reimbSubmitted))
			return false;
		if (reimbTypeId == null) {
			if (other.reimbTypeId != null)
				return false;
		} else if (!reimbTypeId.equals(other.reimbTypeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted="
				+ reimbSubmitted + ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription
				+ ", reimbReceipt=" + reimbReceipt + ", reimbAuthor=" + reimbAuthor + ", reimbResolver=" + reimbResolver
				+ ", reimbStatusId=" + reimbStatusId + ", reimbTypeId=" + reimbTypeId + "]";
	}
	
	

}
