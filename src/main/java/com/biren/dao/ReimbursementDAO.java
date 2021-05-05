package com.biren.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.FileHandelingException;
import com.biren.exception.UploadImageException;
import com.biren.model.Reimbursement;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;
import com.biren.utils.SessionUtility;

import io.javalin.http.UploadedFile;

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
	
	public void denyReimbursement(ReimbursementDTO reimbursementDTO) {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Reimbursement currentReimbursement = (Reimbursement) session.createQuery("FROM Reimbursement r WHERE r.reimbId = :id")
				.setParameter("id", reimbursementDTO.getReimbId()).getSingleResult();
		ReimbursementStatus denied = session.get(ReimbursementStatus.class, 5);
		currentReimbursement.setReimbStatusId(denied);
		currentReimbursement.setReimbResolver(reimbursementDTO.getReimbResolver());
		currentReimbursement.setReimbResolved(reimbursementDTO.getReimbResolved());
		
		session.save(currentReimbursement);
		tx1.commit();
		return;
	}

	public void setImageOfReimbursement(int currentReimbId, UploadedFile file) throws UploadImageException {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Blob blob = null;
		//blob = new SerialBlob((Blob) file.component1());
		//blob = (Blob) file.component1();
		byte[] imageByteArray = read((ByteArrayInputStream) file.component1());
		try {
			blob = new javax.sql.rowset.serial.SerialBlob(imageByteArray);
		} catch (SerialException e) {
			throw new UploadImageException("Cannot read the uploaded file. Message: "+e.getMessage());
		} catch (SQLException e) {
			throw new UploadImageException("Cannot read the uploaded file. Message: "+e.getMessage());
		}
		Reimbursement currentReimbursement = (Reimbursement) session.createQuery("FROM Reimbursement r WHERE r.reimbId = :id")
				.setParameter("id", currentReimbId).getSingleResult();
		currentReimbursement.setReimbReceipt(blob);
		session.save(currentReimbursement);
		tx1.commit();
		return;
	}
	
	public byte[] read(ByteArrayInputStream bais) throws UploadImageException {
	     byte[] array = new byte[bais.available()];
	     try {
			bais.read(array);
		} catch (IOException e) {
			throw new UploadImageException("Cannot read the uploaded file. Message: "+e.getMessage());
		}

	     return array;
	}

	
	public byte[] getImageOfReimbursement(int currentReimbId) throws FileHandelingException {
		session = SessionUtility.getSession().getCurrentSession();
		Transaction tx1 = session.beginTransaction();
		Reimbursement currentReimbursement = (Reimbursement) session.createQuery("FROM Reimbursement r WHERE r.reimbId = :id")
				.setParameter("id", currentReimbId).getSingleResult();
		Blob blob = currentReimbursement.getReimbReceipt();
		byte[] bytes = null;
		try {
			bytes = blob.getBytes(1l, (int)blob.length());
		} catch (SQLException e) {
			throw new FileHandelingException("Something went wrong while retreiving the receipt data. Message: "+e.getMessage());
		}
		tx1.commit();
		return bytes;
	}

}
