package com.biren.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biren.exception.BadParameterException;
import com.biren.exception.FileHandelingException;
import com.biren.exception.LoginException;
import com.biren.exception.ReimbursementsNotFoundException;
import com.biren.exception.UploadImageException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionMapper implements Controller {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

	public ExceptionMapper() {
		super();
	}

	private ExceptionHandler<BadParameterException> badParameterException = (e,ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(400);
	};
	
	private ExceptionHandler<LoginException> loginException = (e,ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(401);
	};
	
	private ExceptionHandler<ReimbursementsNotFoundException> reimbursementsNotFoundException = (e,ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(404);
	};
	
	private ExceptionHandler<UploadImageException> uploadImageException = (e,ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(400);
	};
	
	private ExceptionHandler<FileHandelingException> fileHandelingException = (e,ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(500);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterException);
		app.exception(LoginException.class, loginException);
		app.exception(ReimbursementsNotFoundException.class, reimbursementsNotFoundException);
		app.exception(UploadImageException.class, uploadImageException);
		app.exception(FileHandelingException.class, fileHandelingException);
		
	}

}
