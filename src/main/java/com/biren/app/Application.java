package com.biren.app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biren.controller.Controller;
import com.biren.controller.LoginController;
import com.biren.controller.StaticFileController;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;
import com.biren.model.UserRoles;
import com.biren.utils.SessionUtility;

import io.javalin.Javalin;

public class Application {

	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
//		Session session = SessionUtility.getSession();
//		
//		Transaction tx1 = session.beginTransaction();
//		
//		ReimbursementStatus submitted = new ReimbursementStatus("submitted");
//		ReimbursementStatus pending = new ReimbursementStatus("pending");
//		ReimbursementStatus approved = new ReimbursementStatus("approved");
//		ReimbursementStatus paid = new ReimbursementStatus("paid");
//		ReimbursementStatus denied = new ReimbursementStatus("denied");
//		
//		session.save(submitted);
//		session.save(pending);
//		session.save(approved);
//		session.save(paid);
//		session.save(denied);
//		
//		tx1.commit();
//		
////		System.out.println(submitted);
//		
////		ReimbursementStatus submitted = session.get(ReimbursementStatus.class, 1);
////		System.out.println(submitted);
//		
//		Transaction tx2 = session.beginTransaction();
//		
//		ReimbursementType travelLodging = new ReimbursementType("travel_lodging");
//		ReimbursementType travelMeal = new ReimbursementType("travel_meal");
//		ReimbursementType travelTrip = new ReimbursementType("travel_trip");
//		ReimbursementType travelGas = new ReimbursementType("travel_gas");
//		ReimbursementType travelIncidental = new ReimbursementType("travel_incidental");
//		
//		session.save(travelLodging);
//		session.save(travelMeal);
//		session.save(travelTrip);
//		session.save(travelGas);
//		session.save(travelIncidental);
//		
//		tx2.commit();
//		
//		
//		Transaction tx3 = session.beginTransaction();
//		
//		UserRoles approver = new UserRoles("approver");
//		UserRoles submitter = new UserRoles("submitter");
//		
//		session.save(approver);
//		session.save(submitter);
//		
//		tx3.commit();
//		
//		
//		Transaction tx4 = session.beginTransaction();
//		
//		User user1 = new User("Himseepenst","sa4EiB6yie","Roberto","Symons","RobertoLSymons@armyspy.com",approver);
//		User user2 = new User("Swerhat","Ohgh1doh","Rebecca","Carrasquillo","RebeccaLCarrasquillo@dayrep.com",approver);
//		User user3 = new User("Evin1990","aipa0Fahnga","Verna","Luman","VernaVLuman@dayrep.com",submitter);
//		User user4 = new User("Knounge","pheeH6bie","Michael","Harris","MichaelKHarris@armyspy.com",submitter);
//		User user5 = new User("Roldho","Oos0iecie","Vernon","Irwin","VernonSIrwin@rhyta.com",submitter);
//		
//		session.save(user1);
//		session.save(user2);
//		session.save(user3);
//		session.save(user4);
//		session.save(user5);
//		
//		tx4.commit();
		
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
