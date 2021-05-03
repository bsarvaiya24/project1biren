package com.biren.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.biren.model.Reimbursement;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;

public class ReimbursementDTO {
	
	private int reimbId;
	private double reimbAmount;
	private Date reimbSubmitted;
	private Date reimbResolved;
	private String reimbDescription;
	private String reimbReceipt;
	private User reimbAuthor;
	private User reimbResolver;
	private ReimbursementStatus reimbStatusId;
	private ReimbursementType reimbTypeId;
	private int reimbStatusIdInt;
	private String reimbTypeIdString;
	private String reimbSubmittedString;
	private String reimbResolvedString;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public ReimbursementDTO() {
		super();
	}
	
	// Approve Reimbursement
	public ReimbursementDTO(int reimbId) {
		super();
		this.reimbId = reimbId;
	}

	public ReimbursementDTO(int reimbId, double reimbAmount, Date reimbSubmitted, User reimbAuthor,
			ReimbursementStatus reimbStatusId, ReimbursementType reimbTypeId) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmittedString = formatter.format(reimbSubmitted);
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}
	
	// /add_reimbursement Controller
	public ReimbursementDTO(double reimbAmount, String reimbSubmittedString, int reimbStatusId, String reimbTypeId) {
		super();
		this.reimbAmount = reimbAmount;
		this.reimbSubmittedString = reimbSubmittedString;
		this.reimbStatusIdInt = reimbStatusId;
		this.reimbTypeIdString = reimbTypeId;
	}
	
	public ReimbursementDTO(double reimbAmount, String reimbSubmitted, User reimbAuthor,
			ReimbursementStatus reimbStatusId, ReimbursementType reimbTypeId) {
		super();
		this.reimbAmount = reimbAmount;
		this.reimbSubmittedString = reimbSubmitted;
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}

	public ReimbursementDTO(int reimbId, double reimbAmount, String reimbSubmitted, User reimbAuthor,
			ReimbursementStatus reimbStatusId, ReimbursementType reimbTypeId) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmittedString = reimbSubmitted;
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}
	
	// /populate_data : getReimbursementsByUser()
	// /add_reimbursement DAO
	public ReimbursementDTO(Reimbursement r) {
		this.reimbId = r.getReimbId();
		this.reimbAmount = r.getReimbAmount();
		this.reimbSubmittedString = formatter.format(r.getReimbSubmitted());
		//Null
		try {
			this.reimbResolvedString = formatter.format(r.getReimbResolved());
		} catch (NullPointerException e1) {
			// TODO: Log NullPointerException
			this.reimbResolvedString = null;
		}
		//TODO: Turn to UserDTO
		this.reimbAuthor = r.getReimbAuthor();
		//TODO: Turn to UserDTO
		this.reimbResolver = r.getReimbResolver();
		this.reimbStatusId = r.getReimbStatusId();
		this.reimbTypeId = r.getReimbTypeId();
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

	public SimpleDateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	public int getReimbStatusIdInt() {
		return reimbStatusIdInt;
	}

	public void setReimbStatusIdInt(int reimbStatusIdInt) {
		this.reimbStatusIdInt = reimbStatusIdInt;
	}

	public String getReimbTypeIdString() {
		return reimbTypeIdString;
	}

	public void setReimbTypeIdString(String reimbTypeIdString) {
		this.reimbTypeIdString = reimbTypeIdString;
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

	public String getReimbSubmittedString() {
		return reimbSubmittedString;
	}

	public void setReimbSubmittedString(String reimbSubmittedString) {
		this.reimbSubmittedString = reimbSubmittedString;
	}

	public String getReimbResolvedString() {
		return reimbResolvedString;
	}

	public void setReimbResolvedString(String reimbResolvedString) {
		this.reimbResolvedString = reimbResolvedString;
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
		result = prime * result + ((reimbResolvedString == null) ? 0 : reimbResolvedString.hashCode());
		result = prime * result + ((reimbResolver == null) ? 0 : reimbResolver.hashCode());
		result = prime * result + ((reimbStatusId == null) ? 0 : reimbStatusId.hashCode());
		result = prime * result + reimbStatusIdInt;
		result = prime * result + ((reimbSubmitted == null) ? 0 : reimbSubmitted.hashCode());
		result = prime * result + ((reimbSubmittedString == null) ? 0 : reimbSubmittedString.hashCode());
		result = prime * result + ((reimbTypeId == null) ? 0 : reimbTypeId.hashCode());
		result = prime * result + ((reimbTypeIdString == null) ? 0 : reimbTypeIdString.hashCode());
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
		ReimbursementDTO other = (ReimbursementDTO) obj;
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
		if (reimbResolvedString == null) {
			if (other.reimbResolvedString != null)
				return false;
		} else if (!reimbResolvedString.equals(other.reimbResolvedString))
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
		if (reimbStatusIdInt != other.reimbStatusIdInt)
			return false;
		if (reimbSubmitted == null) {
			if (other.reimbSubmitted != null)
				return false;
		} else if (!reimbSubmitted.equals(other.reimbSubmitted))
			return false;
		if (reimbSubmittedString == null) {
			if (other.reimbSubmittedString != null)
				return false;
		} else if (!reimbSubmittedString.equals(other.reimbSubmittedString))
			return false;
		if (reimbTypeId == null) {
			if (other.reimbTypeId != null)
				return false;
		} else if (!reimbTypeId.equals(other.reimbTypeId))
			return false;
		if (reimbTypeIdString == null) {
			if (other.reimbTypeIdString != null)
				return false;
		} else if (!reimbTypeIdString.equals(other.reimbTypeIdString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementDTO [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted="
				+ reimbSubmitted + ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription
				+ ", reimbReceipt=" + reimbReceipt + ", reimbAuthor=" + reimbAuthor + ", reimbResolver=" + reimbResolver
				+ ", reimbStatusId=" + reimbStatusId + ", reimbTypeId=" + reimbTypeId + ", reimbStatusIdInt="
				+ reimbStatusIdInt + ", reimbTypeIdString=" + reimbTypeIdString + ", reimbSubmittedString="
				+ reimbSubmittedString + ", reimbResolvedString=" + reimbResolvedString + "]";
	}
	
}
