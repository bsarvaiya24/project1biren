package com.biren.controller;

import java.util.List;

import org.hibernate.Session;

import com.biren.dto.MessageDTO;
import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.model.User;
import com.biren.service.SubmitterService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SubmitterController implements Controller {
	
	private SubmitterService submitterService = new SubmitterService();

	public SubmitterController() {
		super();
	}

	//Done
	private Handler populateDataByUserId = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			List<ReimbursementDTO> userReimbursementDTO = submitterService.getReimbursementsByUser(user);
			ctx.json(userReimbursementDTO);
			ctx.status(200);
		}
	};
	
	//Done
	private Handler addReimbursement = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			ReimbursementDTO reimbursementDTO = null;
			try {
				reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
			} catch (NullPointerException e1) {
				throw new BadParameterException("Null values received for parameter(s). Message: "+e1.getMessage());
			}
			reimbursementDTO.setReimbAuthor(user);
			ReimbursementDTO returnReimbursementDTO = submitterService.addReimbursement(reimbursementDTO);
//			ctx.json(returnReimbursementDTO);
			ctx.status(200);
		}
	};

	//TODO
	private Handler latestApproverData = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			List<ReimbursementDTO> allReimbursementDTO = submitterService.latestApproverData();
			ctx.json(allReimbursementDTO);
			ctx.status(200);
		}
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/populate_data", populateDataByUserId);
		app.post("/add_reimbursement", addReimbursement);
		app.get("/latest_approver_data", latestApproverData);
	}

}
