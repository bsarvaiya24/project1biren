package com.biren.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.biren.dto.LoginDTO;
import com.biren.dto.MessageDTO;
import com.biren.model.User;
import com.biren.service.LoginService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller {
	
	private LoginService loginService;

	public LoginController() {
		this.loginService = new LoginService();
	}
	
	private Handler loginHandler = (ctx) -> {
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		User user = loginService.login(loginDTO);
		
		ctx.sessionAttribute("currentlyLoggedInUser", user);
	};
	
	private Handler getLoginHandler(String classpathPath) {
		return ctx -> {
			InputStream is = StaticFileController.class.getResourceAsStream(classpathPath);
			
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
		if(user == null) {
			ctx.json(new MessageDTO("User is not logged in."));
		} else {
			ctx.json(user);
		}
	};
	
	private Handler logoutHandler = (ctx) -> {
		ctx.req.getSession().invalidate();
		ctx.html("Logout successful");
		ctx.status(200);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/login", loginHandler);
		app.get("/", getLoginHandler("/static/index_login.html"));
		app.get("/login", getLoginHandler("/static/index_login.html"));
		app.get("/current_user", currentUserHandler);
		app.post("/logout", logoutHandler);	
	}

}
