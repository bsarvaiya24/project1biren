package com.biren.service;

import java.util.List;

import org.hibernate.Session;

import com.biren.dao.UserDAO;
import com.biren.dto.LoginDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.LoginException;
import com.biren.model.Reimbursement;
import com.biren.model.User;
import com.biren.utils.SessionUtility;

public class LoginService {
	
	private UserDAO userDAO;
	

	public LoginService() {
		this.userDAO = new UserDAO();
	}
	
	public LoginService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User login(LoginDTO loginDTO, Session session) throws BadParameterException, LoginException {
		
		if(loginDTO.getUsername().trim().equals("") || loginDTO.getPassword().trim().equals("")) {
			throw new BadParameterException("Cannot have blank username and/or password");
		}
		User user = userDAO.getUserByUsernameAndPassword(loginDTO,session);
		if(user == null) {
			throw new LoginException("User was not able to login with given username and password.");
		}
		
		return user;
	}

	public List<Reimbursement> getReimbursementByUser(User user, Session session) throws LoginException, BadParameterException {
		if(user.getUsername().trim().equals("") || user.getPassword().trim().equals("")) {
			throw new BadParameterException("Cannot have blank username and/or password");
		}
		List<Reimbursement> returnedUserReimbursements = userDAO.getReimbursementsByUser(user,session);
		if(returnedUserReimbursements == null) {
			throw new LoginException("User was not able to login with given username and password.");
		}
		
		return returnedUserReimbursements;
	}

}
