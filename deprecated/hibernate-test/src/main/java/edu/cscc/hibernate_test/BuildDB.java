package edu.cscc.hibernate_test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.hibernate.*;

public class BuildDB {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(BuildDB.class.getName());

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
			
		}
		

		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			Location location = new Location("Main", State.NEW_YORK);
			session.save(location);
			session.save(new Customer("Jakab Gipsz", "jgipsz@example.com", "password", location));
			session.save(new Customer("Captain Nemo", "cptnemo@example.com", "test123", location));
			location = new Location ("Columbus", State.OHIO);
			session.save(location);
			session.save(new Customer("John Smith", "jsmith@example.com", "deadbeef", location));
			session.getTransaction().commit();

		}
		catch (Exception e) {
			logger.error("Failed to create database", e); //$NON-NLS-1$
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
