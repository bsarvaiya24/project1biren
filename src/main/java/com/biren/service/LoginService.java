package com.biren.service;

import com.biren.dao.UserDAO;
import com.biren.dto.LoginDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.LoginException;
import com.biren.model.User;

public class LoginService {
	
	private UserDAO userDAO;

	public LoginService() {
		this.userDAO = new UserDAO();
	}
	
	public LoginService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User login(LoginDTO loginDTO) throws BadParameterException, LoginException {
		if(loginDTO.getUsername().trim().equals("") || loginDTO.getPassword().trim().equals("")) {
			throw new BadParameterException("Cannot have blank username and/or password");
		}
		User user = userDAO.getUserByUsernameAndPassword(loginDTO);
		if(user == null) {
			throw new LoginException("User was not able to login with given username and password.");
		}
		
		return user;
	}

}
