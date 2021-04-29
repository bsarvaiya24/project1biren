package com.biren.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.internal.build.AllowSysOut;

import com.biren.dto.MessageDTO;
import com.biren.model.Reimbursement;
import com.biren.model.User;
import com.biren.service.LoginService;
import com.biren.service.SubmitterService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SubmitterController implements Controller {
	
	private SubmitterService submitterService;
	protected static Session session;

	public SubmitterController() {
		super();
	}
	
	public SubmitterController(Session session) {
		super();
		this.session = session;
	}
	

	public Session getSession() {
		return session;
	}

	public static void setSession(Session currentSession) {
		session = currentSession;
	}

	private Handler populateDataByUserId = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		System.out.println("Inside populateData handler");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			System.out.println("User found");
			List<Reimbursement> userReimbursement = submitterService.getReimbursementByUser(user,session);
			System.out.println("reimbursements found");
			ctx.json(userReimbursement);
			ctx.status(200);
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		// TODO Auto-generated method stub
		app.post("/populatedata", populateDataByUserId);
	}

}
