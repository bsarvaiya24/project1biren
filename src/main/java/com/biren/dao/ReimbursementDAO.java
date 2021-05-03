package com.biren.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.model.Reimbursement;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;
import com.biren.utils.SessionUtility;

public class ReimbursementDAO {
	
//	Session session = SessionUtility.getSession().getCurrentSession();
	Session session = null;
	List<User> reimbursements = new ArrayList<>();
	
	SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

	public ReimbursementDAO() {
		super();
	}
	
	//Done
	public List<Reimbursement> getReimbursementsByUser(User user) {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		List<Reimbursement> userReimbursements = (List<Reimbursement>) session.createQuery("FROM Reimbursement r WHERE r.reimbAuthor = :user")
				.setParameter("user", user).getResultList();
		tx1.commit();
		return userReimbursements;
	}

	public ReimbursementDTO addReimbursement(ReimbursementDTO reimbursementDTO) throws BadParameterException {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		double dtoAmount = reimbursementDTO.getReimbAmount();
		String dtoStringDate = reimbursementDTO.getReimbSubmittedString();
		Date dtoDate = null;
		try {
			dtoDate = parser.parse(dtoStringDate);
		} catch (ParseException e) {
			throw new BadParameterException("Invalid date input found. Message: "+e.getMessage());
		}
		User dtoAuthor = reimbursementDTO.getReimbAuthor();
		ReimbursementStatus dtoReimbStatus = (ReimbursementStatus) session.createQuery("FROM ReimbursementStatus rs WHERE rs.reimbStatusId=:dtoStatusId")
				.setParameter("dtoStatusId", reimbursementDTO.getReimbStatusIdInt()).getSingleResult();
		
		ReimbursementType dtoReimbType = (ReimbursementType) session.createQuery("FROM ReimbursementType rt WHERE rt.reimbType=:dtoType")
				.setParameter("dtoType", reimbursementDTO.getReimbTypeIdString()).getSingleResult();

		// TODO: Adding new reimbursement here
		Reimbursement reimb1 = new Reimbursement(dtoAmount,dtoDate,dtoAuthor,dtoReimbStatus,dtoReimbType);
		session.save(reimb1);
		ReimbursementDTO returnReimbursementDTO = new ReimbursementDTO(reimb1);
		tx1.commit();
		return returnReimbursementDTO;
	}

	public List<Reimbursement> latestApproverData() {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		List<Reimbursement> allReimbursements = (List<Reimbursement>) session.createQuery("FROM Reimbursement r").getResultList();
		tx1.commit();
		return allReimbursements;
	}

	public void approveReimbursement(ReimbursementDTO reimbursementDTO) {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Reimbursement currentReimbursement = (Reimbursement) session.createQuery("FROM Reimbursement r WHERE r.reimbId = :id")
				.setParameter("id", reimbursementDTO.getReimbId()).getSingleResult();
		ReimbursementStatus approved = session.get(ReimbursementStatus.class, 3);
		currentReimbursement.setReimbStatusId(approved);
		currentReimbursement.setReimbResolver(reimbursementDTO.getReimbResolver());
		currentReimbursement.setReimbResolved(reimbursementDTO.getReimbResolved());
		
		session.save(currentReimbursement);
		tx1.commit();
		return;
	}

}
