package com.biren.dao;

import java.util.ArrayList;
import java.util.List;

import com.biren.dto.LoginDTO;
import com.biren.model.User;



public class UserDAO {
	
	List<User> users = new ArrayList<>();

	public UserDAO() {
		super();
	}
	
	{
		users.add(new User(1, "admin", "password"));
	}
	
	

	public User getUserByUsernameAndPassword(LoginDTO loginDTO) {
		for(User u:users) {
			if(loginDTO.getUsername().equals(u.getUsername()) && loginDTO.getPassword().equals(u.getPassword())) {
				return u;
			}
		}
		return null;
	}

}
