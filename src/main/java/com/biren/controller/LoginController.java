package com.biren.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.Session;

import com.biren.dto.LoginDTO;
import com.biren.dto.MessageDTO;
import com.biren.exception.StaticFileNotFoundException;
import com.biren.model.Reimbursement;
import com.biren.model.User;
import com.biren.service.LoginService;
import com.biren.utils.SessionUtility;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller {
	
	private LoginService loginService;
	Session session = null;

	public LoginController() {
		this.loginService = new LoginService();
	}
	
	private Handler loginHandler = (ctx) -> {
		session = SessionUtility.getSession();
		SubmitterController.setSession(session);
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		User user = loginService.login(loginDTO,session);
		System.out.println(user);
		ctx.sessionAttribute("currentlyLoggedInUser", user);
		// ctx.json(user);
		ctx.status(200);
	};
	
	private Handler getLoginHandler(String classpathPath) {
		return ctx -> {
			InputStream is = LoginController.class.getResourceAsStream(classpathPath);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read;
			byte[] buffer = new byte[1024];
			
			while((read = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}
			
			byte[] ourFileInBytes = baos.toByteArray();
			
			String html = new String(ourFileInBytes);
			ctx.html(html);
			ctx.status(200);
		};
	}
	
	private Handler currentUserHandler = (ctx) -> {
		User user = (User) ctx.sessionAttribute("currentlyLoggedInUser");
		if (user == null) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setMessage("User is not currently logged in!");
			ctx.json(messageDTO);
			ctx.status(400);
		} else {
			ctx.json(user);
		}
	};
	
	private Handler logoutHandler = (ctx) -> {
		ctx.req.getSession().invalidate();
		ctx.html("Logout successful");
		ctx.status(200);
	};
	
	private String classpathToString(String classpathPath) throws StaticFileNotFoundException {
		InputStream is = LoginController.class.getResourceAsStream(classpathPath);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read;
		byte[] buffer = new byte[1024];
		
		try {
			while((read = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}
		} catch (IOException e) {
			throw new StaticFileNotFoundException("Something went wrong while trying to get the static file. Message: "+e.getMessage());
		}
		
		byte[] ourFileInBytes = baos.toByteArray();
		
		String html = new String(ourFileInBytes);
		return html;
	}



	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/login", loginHandler);
		app.get("/login", getLoginHandler("/static/index_login.html"));
		app.get("/current_user", currentUserHandler);
		app.post("/logout", logoutHandler);
		
	}

}
