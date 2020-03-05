package com.gamecouch.gcs.gamecouchsystem;
import java.math.BigDecimal;

import org.hibernate.*;

import com.gamecouch.gcs.accounting.*;


/**
 * Deletes and rebuilds the database tables, but only if /gamecouchsystem/src/main/resources/hibernate.cfg.xml has hbm2ddl.auto set to "create".
 * It should normally be set to "validate".
 * Note that you'll get a lot of errors saying "Cannot drop the table" and "Cannot find the object" the first time you run it with new tables.
 * 
 * @author Alan Bolte
 *
 */
public class BuildDB {
	
	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			
			//Customers and Locations
			Location location = new Location("Main", State.NEW_YORK);
			session.save(location);
			session.save(new Customer("Alanbolte", "abolte@example.com", "password", location));
			session.save(new Customer("Captain Nemo", "cptnemo@example.com", "test123", location));
			location = new Location ("Columbus", State.OHIO);
			session.save(location);
			session.save(new Customer("John Smith", "jsmith@example.com", "deadbeef", location));
			
			//Accounts
			session.save(new Account(10102, "Cash", new BigDecimal(10000)));
			session.save(new Account(10112, "Accounts Receivable"));
			session.save(new Account(10156, "Inventory", new BigDecimal(500000)));
			session.save(new Account(10201, "Accounts Payable"));
			
			
			
			session.getTransaction().commit();

		}
		catch (Exception e) {
		}
	}

}
