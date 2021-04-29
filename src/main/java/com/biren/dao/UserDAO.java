package com.biren.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.biren.dto.LoginDTO;
import com.biren.model.Reimbursement;
import com.biren.model.User;



public class UserDAO {
	
	List<User> users = new ArrayList<>();
	List<Reimbursement> userTransactions = new ArrayList<>();

	public UserDAO() {
		super();
	}
	
	{
//		users.add(new User(1, "admin", "password"));
	}
	
	public void getUsers(Session session){
		
		this.users = (List<User>) session.createQuery("FROM User u").getResultList();
		
	}
	
	public void getUsersById(User user,Session session){
		
		this.users = (List<User>) session.createQuery("FROM User u").getResultList();
		
	}

	public User getUserByUsernameAndPassword(LoginDTO loginDTO, Session session) {
		this.getUsers(session);
		for(User u:users) {
			if(loginDTO.getUsername().equals(u.getUsername()) && loginDTO.getPassword().equals(u.getPassword())) {
				return u;
			}
		}
		return null;
	}

	public List<Reimbursement> getReimbursementsByUser(User user, Session session) {
		this.userTransactions = (List<Reimbursement>) session.createQuery("FROM Reimbursement r WHERE r.reimbAuthor = :user")
				.setParameter("user", user).getResultList();
		return userTransactions;
	}

}
