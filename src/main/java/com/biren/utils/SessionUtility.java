package com.biren.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtility {

	private static SessionFactory sessionFactory;
	
	public synchronized static Session getSession() {
		if(sessionFactory == null) {
			sessionFactory = new Configuration()
								.setProperty("hibernate.connection.username", System.getenv("AWS_DB_USERNAME"))
								.setProperty("hibernate.connection.password", System.getenv("AWS_DB_PASSWORD"))
								.configure("hibernate.cfg.xml")
								.buildSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
}
