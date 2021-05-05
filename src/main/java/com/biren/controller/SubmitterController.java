package com.biren.controller;

import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import com.biren.dto.MessageDTO;
import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.model.User;
import com.biren.service.SubmitterService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class SubmitterController implements Controller {
	
	private SubmitterService submitterService = new SubmitterService();

	public SubmitterController() {
		super();
	}

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
	
	private Handler uploadPendingReimbursements = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			System.out.println("user found");
			List<ReimbursementDTO> userReimbursementDTO = submitterService.getUploadPendingReimbursements(user);
			ctx.json(userReimbursementDTO);
			ctx.status(200);
		}
	};
	
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
	
	private Handler uploadReimbursementImage = (ctx) -> {
		String currentReimbIdString = ctx.pathParam("reimbId");
		UploadedFile file = ctx.uploadedFiles("photo").get(0);
		System.out.println(file);
		submitterService.setImageOfReimbursement(currentReimbIdString,file);
		ctx.status(200);
	};
	
	private Handler getReimbursementImage = (ctx) -> {
		String currentReimbIdString = ctx.pathParam("reimbId");
		System.out.println("request to get image for reimbursement id:"+currentReimbIdString);
//		UploadedFile file = ctx.uploadedFiles("photo").get(0);
//		System.out.println(file);
		InputStream targetStream = submitterService.getImageOfReimbursement(currentReimbIdString);
		ctx.result(targetStream);
		ctx.status(200);
	};

	private Handler latestApproverData = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			boolean allowedUser = submitterService.checkIfUserIsApprover(user);
			if(allowedUser) {
				List<ReimbursementDTO> allReimbursementDTO = submitterService.latestApproverData();
				ctx.json(allReimbursementDTO);
				ctx.status(200);
			} else {
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setMessage("User is not allowed to access this URL!");
				ctx.json(messageDTO);
				ctx.status(403);
			}
		}
	};
	
	private Handler approveReimbursement = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			boolean allowedUser = submitterService.checkIfUserIsApprover(user);
			if(allowedUser) {
				ReimbursementDTO reimbursementDTO = null;
				try {
					reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
					reimbursementDTO.setReimbResolver(user);
					Date date = new Date();
					System.out.println(date);
					reimbursementDTO.setReimbResolved(date);
				} catch (NullPointerException e1) {
					throw new BadParameterException("Null values received for parameter(s). Message: "+e1.getMessage());
				}
				submitterService.approveReimbursement(reimbursementDTO);
				ctx.status(200);
			} else {
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setMessage("User is not allowed to access this URL!");
				ctx.json(messageDTO);
				ctx.status(403);
			}
		}
	};
	
	private Handler denyReimbursement = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			boolean allowedUser = submitterService.checkIfUserIsApprover(user);
			if(allowedUser) {
				ReimbursementDTO reimbursementDTO = null;
				try {
					reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
					reimbursementDTO.setReimbResolver(user);
					Date date = new Date();
					System.out.println(date);
					reimbursementDTO.setReimbResolved(date);
				} catch (NullPointerException e1) {
					throw new BadParameterException("Null values received for parameter(s). Message: "+e1.getMessage());
				}
				submitterService.denyReimbursement(reimbursementDTO);
				ctx.status(200);
			} else {
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setMessage("User is not allowed to access this URL!");
				ctx.json(messageDTO);
				ctx.status(403);
			}
		}
	};
	
	private Handler checkForApprover = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			boolean allowedUser = submitterService.checkIfUserIsApprover(user);
			if(allowedUser) {
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setMessage("true");
				ctx.json(messageDTO);
				ctx.status(200);
			} else {
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.setMessage("false");
				ctx.json(messageDTO);
				ctx.status(403);
			}
		}
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/populate_data", populateDataByUserId);
		app.post("/add_reimbursement", addReimbursement);
		app.post("/upload_reimbursement_image/:reimbId", uploadReimbursementImage);
		app.get("/reimbursement_receipt/:reimbId", getReimbursementImage);
		app.get("/upload_pending_reimbursements", uploadPendingReimbursements);
		
		app.get("/check_for_approver", checkForApprover);
		
		// Approver only URLs
		app.get("/latest_approver_data", latestApproverData);
		app.post("/approve_reimbursement", approveReimbursement);
		app.post("/deny_reimbursement", denyReimbursement);
	}

}
