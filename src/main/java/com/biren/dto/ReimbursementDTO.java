package com.biren.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;

public class ReimbursementDTO {
	
	private int reimbId;
	private double reimbAmount;
	private String reimbSubmitted;
	private String reimbResolved;
	private String reimbDescription;
	private String reimbReceipt;
	private User reimbAuthor;
	private User reimbResolver;
	private ReimbursementStatus reimbStatusId;
	private ReimbursementType reimbTypeId;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public ReimbursementDTO() {
		super();
	}

	public ReimbursementDTO(int reimbId, double reimbAmount, Date reimbSubmitted, User reimbAuthor,
			ReimbursementStatus reimbStatusId, ReimbursementType reimbTypeId) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = formatter.format(reimbSubmitted);
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}

	public ReimbursementDTO(int reimbId, double reimbAmount, String reimbSubmitted, User reimbAuthor,
			ReimbursementStatus reimbStatusId, ReimbursementType reimbTypeId) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbAuthor = reimbAuthor;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}
	

}
