package com.gamecouch.gcs.reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.gamecouch.gcs.accounting.Account;
import com.gamecouch.gcs.accounting.JournalEntry;
import com.gamecouch.gcs.accounting.JournalLine;
import com.gamecouch.gcs.accounting.ReservationInvoice;
import com.gamecouch.gcs.gamecouchsystem.*;

@ManagedBean(name = "reservation")
@RequestScoped
public class ReservationBean {
	private static final double RES4 = 5.0;
	private static final double RES8 = 10.0;
	private static final double USE4 = 30.0;
	private static final double USE8 = 50.0;
	private static final String ALERT = "alert(\"Your reservation has been placed.\\nWe will see you on \" + resDate + \", from \" + resTime + \", at table \" + resTable + \".\\nReturning to the reservation page.\\nThank you!\");\r\n";
	
	private Date reservationDate;
	private byte table;
	private byte[] timeSlot;
	private String testValue = "test";
	private Location location;
	private String script;

	private Lookup lookup = new Lookup();

	public Date getReservationDate() {
		return reservationDate;
	}
	
	public String shortDate() {
		return "'" + LocalDate.ofInstant(reservationDate.toInstant(), ZoneId.of("Z")).toString() + "'";//TODO: real time zones
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public byte getTable() {
		return table;
	}

	public void setTable(byte table) {
		this.table = table;
	}

	public byte[] getTimeSlot() {
		return timeSlot;
	}
	
	public String timeslotString() {
		String start = slotToString(timeSlot[0]);
		String end = slotToString((byte) (timeSlot[timeSlot.length - 1] + 2));
		
		return start + " to " + end;
	}
	
	private String slotToString(byte slot) {
		int hour24 = slot + 12; //2 hour slots, earliest time is currently noon, change this if we offer other times
		if (hour24 < 12)
			return hour24 + " am";
		else if (hour24 == 12)
			return hour24 + " pm";
		else
			return (hour24 - 12) + " pm";
	}

	public void setTimeSlot(byte[] timeSlot) {
		this.timeSlot = timeSlot;
	}

	/*
	public String create(ReservationBean reservation, Customer customer) {
		Reservation newRes = new Reservation();
		newRes.setCustomer(customer);
		newRes.setLocation(customer.getDefaultLocation()); // TODO: allow selection of location

		newRes.setTable((PhysicalTable) lookup.getRowObjectByID(PhysicalTable.class, reservation.getTable()));

		Date startTime = reservation.getReservationDate(); // TODO: not implemented correctly, it only appears to work
		startTime = new Date((startTime.getTime() + (12 + reservation.getTimeSlot()[0] * 3600000))); // attempt to
																											// add
																											// reservation
																											// time to
																											// the date

		newRes.setReservationTime(startTime);
		newRes.setDuration(reservation.getTimeSlot().length * 2 * 3600000);

		lookup.create(newRes);

		return "tableCheckout";
	}*/

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testvalue) {
		this.testValue = testvalue;
	}

	public String getSessionID() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().toString();
	}
	
	public String create(ReservationBean reservation, Customer c) {	

		System.out.println("Creating reservation");
		Date resTime = reservation.getReservationDate(); // TODO: not implemented correctly, it only appears to work
		resTime = new Date((resTime.getTime() + (12 + reservation.getTimeSlot()[0] * 3600000))); // attempt to add reservation time to the date
		int duration = reservation.getTimeSlot().length * 2 * 3600000;
		Location l = reservation.getLocation();
		byte party;//TODO: add actual input for party size or remove this.
		
		
		//get needed objects
		PhysicalTable t = PhysicalTable.getTables().get(reservation.getTable()-1);
		Account cash = (Account) lookup.getRowObjectByStringField(Account.class, "name", "Cash");
		Account unearned = (Account) lookup.getRowObjectByStringField(Account.class, "name", "Unearned Revenues");
		Account revenues = (Account) lookup.getRowObjectByStringField(Account.class, "name", "Revenues");
		Account receivable = (Account) lookup.getRowObjectByStringField(Account.class, "name", "Accounts Receivable");
		
		System.out.println(" Creating reservation journal entry");
		//Journal
		var entry = new JournalEntry();
		entry.setDate(new Date());
		lookup.create(entry);
		

		System.out.println(" Creating reservation journal lines");
		double resFee;//TODO: bigdecimal, and these should be constants somewhere
		double usageFee;
		
		if (t.getSeats() == (byte) 4) {
			resFee = RES4;
			usageFee = USE4 * (duration / 3600000.0); 
			party = 4;
		}
		else {
			//tableFor8
			resFee = RES8;
			usageFee = USE8 * (duration/ 3600000.0);
			party = 8;
		}
			
		
		//Reservation Fee
		lookup.create(new JournalLine(entry, 1, resFee,0.0,cash,""));
		lookup.create(new JournalLine(entry, 2, 0.0,resFee,unearned,"reservation fee"));
				
		//Expected usage fee
		lookup.create(new JournalLine(entry, 3, 0.0,usageFee,revenues,""));
		lookup.create(new JournalLine(entry, 4, usageFee,0.0,receivable,"usage fee"));
		//TODO: might have credit/debit backwards
		
		System.out.println(" Creating invoice");
		
		var invoice = new ReservationInvoice(LocalDate.ofInstant(resTime.toInstant(), ZoneId.systemDefault()),entry);//TODO: real time zones
		lookup.create(invoice);
		
		System.out.println(" Saving reservation");
		
		lookup.create(new Reservation(c,l,resTime,duration,(byte) party,t,invoice));
		
		
		return "tableCheckout";
	}

	public Location getLocation() {
		return location;
	}
	


	public void setLocation(Location location) {
		this.location = location;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public static double getRes4() {
		return RES4;
	}

	public static double getRes8() {
		return RES8;
	}
	
	public String calcFee(int seats) { //unneccessary duplication, and a hack
		double usageFee;
		int duration = getTimeSlot().length * 2 * 3600000;
		if (seats == 4) {

			usageFee = USE4 * (duration / 3600000.0); 

		}
		else {
			//tableFor8

			usageFee = USE8 * (duration/ 3600000.0);

		}
		return "$" + usageFee;
	}
	
}
