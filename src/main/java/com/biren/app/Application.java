package com.biren.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biren.controller.Controller;
import com.biren.controller.LoginController;
import com.biren.controller.StaticFileController;
import com.biren.controller.SubmitterController;
import com.biren.model.Reimbursement;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;
import com.biren.model.UserRoles;
import com.biren.utils.SessionUtility;

import io.javalin.Javalin;

public class Application {

	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
//		Session session = SessionUtility.getSession().openSession();
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
//		
//		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Transaction tx5 = session.beginTransaction();
//		
//		Reimbursement reimb1 = null;
//		Reimbursement reimb2 = null;
//		Reimbursement reimb3 = null;
//		Reimbursement reimb4 = null;
//		Reimbursement reimb5 = null;
//		Reimbursement reimb6 = null;
//		Reimbursement reimb7 = null;
//		Reimbursement reimb8 = null;
//		
//		try {
//			reimb1 = new Reimbursement(50,parser.parse("2020-01-10"),user3,paid,travelTrip);
//			reimb2 = new Reimbursement(100,parser.parse("2020-01-10"),user3,paid,travelLodging);
//			reimb3 = new Reimbursement(20,parser.parse("2020-03-20"),user4,paid,travelMeal);
//			reimb4 = new Reimbursement(200,parser.parse("2020-03-20"),user4,paid,travelLodging);
//			reimb5 = new Reimbursement(150,parser.parse("2020-05-12"),user5,paid,travelTrip);
//			reimb6 = new Reimbursement(70,parser.parse("2020-06-29"),user3,paid,travelGas);
//			reimb7 = new Reimbursement(40,parser.parse("2020-06-29"),user3,paid,travelMeal);
//			reimb8 = new Reimbursement(30,parser.parse("2020-07-02"),user3,paid,travelMeal);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		
//		session.save(reimb1);
//		session.save(reimb2);
//		session.save(reimb3);
//		session.save(reimb4);
//		session.save(reimb5);
//		session.save(reimb6);
//		session.save(reimb7);
//		session.save(reimb8);
//		
//		tx5.commit();
		
		Javalin app = Javalin.create((config) -> {
			config.addStaticFiles("static");
		});
		
		app.before(ctx -> {
			String URI = ctx.req.getRequestURI();
			String httpMethod = ctx.req.getMethod();
			logger.info(httpMethod + " request to endpoint " + URI + " received");
		});
		
		mapControllers(app,new LoginController(), new SubmitterController());
		
		app.start(7000);
		
	}
	
	public static void mapControllers(Javalin app, Controller... controllers) {
		for (Controller c : controllers) {
			c.mapEndpoints(app);
		}
	}

}
