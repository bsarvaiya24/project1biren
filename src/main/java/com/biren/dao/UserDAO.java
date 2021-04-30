package com.biren.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.biren.dto.LoginDTO;
import com.biren.model.Reimbursement;
import com.biren.model.User;
import com.biren.utils.SessionUtility;



public class UserDAO {
	
	
	//Open new session.
//	SessionUtility.getSessionFactory().openSession();
	
//	Session session = SessionUtility.getSession();
	Session session = SessionUtility.getSession().openSession();
	
	
	List<User> users = new ArrayList<>();
//	List<Reimbursement> userReimbursements = new ArrayList<>();

	public UserDAO() {
		super();
	}
	
	{
//		users.add(new User(1, "admin", "password"));
	}
	
	public void getUsers(){
		
		this.users = (List<User>) session.createQuery("FROM User u").getResultList();
		
	}
	
//	public void getUsersById(User user,Session session){
//		
//		this.users = (List<User>) session.createQuery("FROM User u").getResultList();
//		
//	}

	public User getUserByUsernameAndPassword(LoginDTO loginDTO) {
		this.getUsers();
		for(User u:users) {
			System.out.println("Checking user: "+u.getUsername()+" "+u.getPassword());
			if(loginDTO.getUsername().equals(u.getUsername()) && loginDTO.getPassword().equals(u.getPassword())) {
				return u;
			}
		}
		return null;
	}

	public List<Reimbursement> getReimbursementsByUser(User user) {
		List<Reimbursement> userReimbursements = (List<Reimbursement>) session.createQuery("FROM Reimbursement r WHERE r.reimbAuthor = :user")
				.setParameter("user", user).getResultList();
		return userReimbursements;
	}

}
