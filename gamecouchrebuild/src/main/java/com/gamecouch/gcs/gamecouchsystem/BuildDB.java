package com.gamecouch.gcs.gamecouchsystem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.*;

import com.gamecouch.gcs.accounting.*;
import com.gamecouch.gcs.reservation.PhysicalTable;


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
			var cash = new Account(10102, "Cash", true, new BigDecimal(10000));
			session.save(cash);
			session.save(new Account(10112, "Accounts Receivable", true));
			var inventory = new Account(10156, "Inventory", true, new BigDecimal(500000));
			session.save(inventory);
			session.save(new Account(10201, "Accounts Payable", false));
			session.save(new Account(30000, "Revenues", true));
			session.save(new Account(24500, "Unearned Revenues", false));
			session.save(new Account(50000, "Expenses", true));
						
			//Journal
			var entry = new JournalEntry();
			entry.setDate(new Date());
			session.save(entry);
			session.save(new JournalLine(entry, 1, 0.0, 1.25, cash, ""));
			session.save(new JournalLine(entry, 2, 1.25, 0.0, inventory, "Candy"));
			//Vendor
			var vendor = new Vendor("dummyVendor","123 Address St, Columbus OH 43202");
			session.save(vendor);
			
			//Bill
			session.save(new Bill(LocalDate.of(2020, 6, 1),entry, vendor));
			
			
			//PhysicalTables
			session.save(new PhysicalTable((byte)8));

			session.save(new PhysicalTable((byte)4));

			session.save(new PhysicalTable((byte)8));

			session.save(new PhysicalTable((byte)4));
			
			//commit
			session.getTransaction().commit();
			
			
		}
		catch (Exception e) {
		}
	}

}
