package com.biren.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.biren.dao.UserDAO;
import com.biren.dto.ReimbursementDTO;
import com.biren.model.Reimbursement;
import com.biren.model.User;

public class SubmitterService {
	
	private UserDAO userDAO = new UserDAO();

	public SubmitterService() {
		super();
	}

	public List<Reimbursement> getReimbursementsByUser(User user, Session session) {
		List<Reimbursement> userReimbursements = userDAO.getReimbursementsByUser(user);
		List<ReimbursementDTO> userReimbursementsDTO = new ArrayList<ReimbursementDTO>();
		
		//TODO
		for(Reimbursement r:userReimbursements) {
//			userReimbursementsDTO.add(new ReimbursementDTO(r));
		}

		return userReimbursements;
	}

}
