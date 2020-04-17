package com.gamecouch.gcs.gamecouchsystem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.*;

import com.gamecouch.gcs.accounting.*;
import com.gamecouch.gcs.reservation.*;


/**
 * Deletes and rebuilds the database tables, but only if /gamecouchsystem/src/main/resources/hibernate.cfg.xml has hbm2ddl.auto set to "create".
 * It should normally be set to "validate".
 * Note that you'll get a lot of errors saying "Cannot drop the table" and "Cannot find the object" the first time you run it with new tables.
 * 
 * @author Alan Bolte
 *
 */
public class BuildDB {
	static Account cash;
	static Account receivable;
	static Account inventory;
	static Account payable;
	static Account revenues;
	static Account unearned;
	static Account expenses;
	
	public static void main(String[] args) {
		System.out.println("Opening session");
		
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			
			System.out.println("Starting Build");
			
			//Customers and Locations
			Location location = new Location("Main", State.NEW_YORK);
			session.save(location);
			session.save(new Customer("Alanbolte", "abolte@example.com", "password", location));
			session.save(new Customer("Captain Nemo", "cptnemo@example.com", "test123", location));
			location = new Location ("Columbus", State.OHIO);
			session.save(location);
			var customer = new Customer("John Smith", "jsmith@example.com", "deadbeef", location);
			session.save(customer);
			
			
			//Accounts 

			System.out.println("Creating Accounts");
			cash = new Account(10102, "Cash", true);
			session.save(cash);
			receivable = new Account(10112, "Accounts Receivable", true);
			session.save(receivable);
			inventory = new Account(10156, "Inventory", true);
			session.save(inventory);
			payable = new Account(10201, "Accounts Payable", false);
			session.save(payable);
			revenues = new Account(30000, "Revenues", false);
			session.save(revenues);
			unearned = new Account(24500, "Unearned Revenues", false);
			session.save(unearned);
			expenses = new Account(50000, "Expenses", true);
			session.save(expenses);
			

			System.out.println("Creating default journal entries");
			//Starting Cash
			var entry = new JournalEntry();
			entry.setDate(new Date());
			session.save(entry);
			session.save(new JournalLine(entry, 1, 20000.0, 0.0, cash, "Previous Year"));
			
			
			//Bills	
			createBill(session, payable, inventory, 250.12, "D&D handbooks", 1);
			createBill(session, payable, inventory, 100.66, "Bulk Dice", 2);
			createBill(session, payable, inventory, 10000.0, "Plastic junk", 5);
			createBill(session, payable, inventory, 25.99, "Custom dice", 8);
			createBill(session, cash, expenses, 500.0, "Cleaners", 11);
			
			
			
			//PhysicalTables
			final var tableFor8 = new PhysicalTable((byte)8);
			session.save(tableFor8);
			
			final var tableFor4 = new PhysicalTable((byte)4);
			session.save(tableFor4);

			session.save(new PhysicalTable((byte)8));

			session.save(new PhysicalTable((byte)4));
			

			
			//Reservations
			final Date RES_TIME = new Date(new Date().getTime() + 72 * 3600000);
			final int HOUR = 3600000;
			
			System.out.println("Creating reservations"); //TODO: vary these values
			createReservation(session, customer, location, RES_TIME, 2* HOUR, 2, tableFor4);
			createReservation(session, customer, location, RES_TIME, 4*HOUR, 4, tableFor4);
			createReservation(session, customer, location, RES_TIME, 2*HOUR, 4, tableFor4);
			createReservation(session, customer, location, RES_TIME, 2*HOUR, 7, tableFor8);
			createReservation(session, customer, location, RES_TIME, 6*HOUR, 8, tableFor8);
			
			System.out.println("Committing");
			//commit
			session.getTransaction().commit();
			
			
		}
		catch (Exception e) {
		}
		System.out.println("Build complete. Remember to set hibernate.cfg to validate");
	}
	
	private static void createBill(Session session, Account payOrCash, Account invOrExp, double amount, String note, int vendorID) {
		//Journal
		var entry = new JournalEntry();
		entry.setDate(new Date());
		session.save(entry);
		session.save(new JournalLine(entry, 1, 0.0, amount, payOrCash, ""));
		session.save(new JournalLine(entry, 2, amount, 0.0, invOrExp, note));
		//TODO: might have credit/debit backwards

		//Vendor
		Lookup lookup = new Lookup();
		Vendor vendor = (Vendor) lookup.getRowObjectByID(Vendor.class, vendorID); //this only works because vendor is in import.sql
		

		session.save(new Bill(LocalDate.of(2020, 6, 1),entry, vendor));
	}
	
	private static void createReservation(Session session, Customer c, Location l, Date resTime, int duration, int party, PhysicalTable table) {
		
		System.out.println(" Creating reservation journal entry");
		//Journal
		var entry = new JournalEntry();
		entry.setDate(new Date());
		session.save(entry);
		

		System.out.println(" Creating reservation journal lines");
		double resFee;//TODO: bigdecimal, and these should be constants somewhere
		double usageFee;
		
		if (table.getSeats() == (byte) 4) {
			resFee = 5.0;
			usageFee = 30.0 * (duration / 3600000.0); 
		}
		else {
			//tableFor8
			resFee = 10.0;
			usageFee = 50.0 * (duration/ 3600000.0);
		}
			
		
		//Reservation Fee
		session.save(new JournalLine(entry, 1, resFee,0.0,cash,""));
		session.save(new JournalLine(entry, 2, 0.0,resFee,unearned,"reservation fee"));
				
		//Expected usage fee
		session.save(new JournalLine(entry, 3, 0.0,usageFee,revenues,""));
		session.save(new JournalLine(entry, 4, usageFee,0.0,receivable,"usage fee"));
		//TODO: might have credit/debit backwards
		
		System.out.println(" Creating invoice");
		
		var invoice = new ReservationInvoice(LocalDate.ofInstant(resTime.toInstant(), ZoneId.systemDefault()),entry);//TODO: real time zones
		session.save(invoice);
		
		System.out.println(" Saving reservation");
		
		session.save(new Reservation(c,l,resTime,duration,(byte) party,table,invoice));
	}
}
