package com.biren.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.biren.dao.ReimbursementDAO;
import com.biren.dao.UserDAO;
import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.ReimbursementsNotFoundException;
import com.biren.model.Reimbursement;
import com.biren.model.User;

public class SubmitterService {
	
	private UserDAO userDAO = new UserDAO();
	private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();

	public SubmitterService() {
		super();
	}

	//Done
	public List<ReimbursementDTO> getReimbursementsByUser(User user) throws ReimbursementsNotFoundException {
		List<Reimbursement> userReimbursements = reimbursementDAO.getReimbursementsByUser(user);
		List<ReimbursementDTO> userReimbursementsDTO = new ArrayList<ReimbursementDTO>();
//		if(userReimbursementsDTO.isEmpty()) {
//			throw new ReimbursementsNotFoundException("No Reimbursements found for the given user");
//		}
		userReimbursements.sort((o1,o2) -> o2.getReimbSubmitted().compareTo(o1.getReimbSubmitted()));
		for(Reimbursement r:userReimbursements) {
			userReimbursementsDTO.add(new ReimbursementDTO(r));
		}
		return userReimbursementsDTO;
	}

	public ReimbursementDTO addReimbursement(ReimbursementDTO reimbursementDTO) throws BadParameterException, ReimbursementsNotFoundException {
		ReimbursementDTO returnReimbursementDTO = reimbursementDAO.addReimbursement(reimbursementDTO);
		if(returnReimbursementDTO == null) {
			throw new ReimbursementsNotFoundException("Recently added reimbursement not found.");
		}
		return returnReimbursementDTO;
	}

	public List<ReimbursementDTO> latestApproverData() {
		List<Reimbursement> allReimbursements = reimbursementDAO.latestApproverData();
		List<ReimbursementDTO> allReimbursementsDTO = new ArrayList<ReimbursementDTO>();
		allReimbursements.sort((o1,o2) -> o2.getReimbSubmitted().compareTo(o1.getReimbSubmitted()));
		for(Reimbursement r:allReimbursements) {
			allReimbursementsDTO.add(new ReimbursementDTO(r));
		}
		return allReimbursementsDTO;
	}

	public void approveReimbursement(ReimbursementDTO reimbursementDTO) {
		reimbursementDAO.approveReimbursement(reimbursementDTO);
		return;
	}

	public boolean checkIfUserIsApprover(User user) {
//		boolean allowedUser = userDAO.getUserByUser(user);
		if(user.getRoleId().getErsUserRoleId() == 1) {
			return true;
		}
		return false;
	}

}
