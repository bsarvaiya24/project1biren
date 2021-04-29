package com.biren.service;

import java.util.List;

import org.hibernate.Session;

import com.biren.dao.UserDAO;
import com.biren.model.Reimbursement;
import com.biren.model.User;

public class SubmitterService {
	
	private UserDAO userDAO;

	public SubmitterService() {
		super();
	}

	public List<Reimbursement> getReimbursementByUser(User user, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

}
