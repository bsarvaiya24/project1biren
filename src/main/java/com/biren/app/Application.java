package com.biren.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biren.controller.Controller;
import com.biren.controller.LoginController;
import com.biren.controller.StaticFileController;

import io.javalin.Javalin;

public class Application {

	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create((config) -> {
			config.addStaticFiles("static");
		});
		
		app.before(ctx -> {
			String URI = ctx.req.getRequestURI();
			String httpMethod = ctx.req.getMethod();
			logger.info(httpMethod + " request to endpoint " + URI + " received");
		});
		
		mapControllers(app,new LoginController());
		
		app.start(7000);
		
	}
	
	public static void mapControllers(Javalin app, Controller... controllers) {
		for (Controller c : controllers) {
			c.mapEndpoints(app);
		}
	}

}
