package com.biren.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	public User login(LoginDTO loginDTO) throws BadParameterException, LoginException {
		if(loginDTO.getUsername().trim().equals("") || loginDTO.getPassword().trim().equals("")) {
			throw new BadParameterException("Cannot have blank username and/or password");
		}
		String passwordString = loginDTO.getPassword();
		String passwordHash = hashPassword(passwordString);
		loginDTO.setPassword(passwordHash);
		User user = userDAO.getUserByUsernameAndPassword(loginDTO);
		if(user == null) {
			throw new LoginException("User was not able to login with given username and password.");
		}
		return user;
	}
	
	public static String hashPassword(String password) {
        
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
        
    }


}
