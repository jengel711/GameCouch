package com.gamecouch.gcs.reservation;


import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.gamecouch.gcs.gamecouchsystem.*;

@ManagedBean(name="reservation")
@ViewScoped
public class ReservationBean {
	private Date reservationDate;
	private byte table;
	private byte[] timeSlot;
	private String testValue = "test";

	private Lookup lookup = new Lookup();
	
	public Date getReservationDate() {
		return reservationDate;
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
	public void setTimeSlot(byte[] timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public String create(ReservationBean reservation, Customer customer) {
		Reservation newRes = new Reservation();
		newRes.setCustomer(customer);
		newRes.setLocation(customer.getDefaultLocation()); //TODO: allow selection of location

		newRes.setTable((PhysicalTable) lookup.getRowObjectByID(PhysicalTable.class, reservation.getTable()));
		
		Date startTime = reservation.getReservationDate(); //TODO: not implemented correctly, it only appears to work
		startTime = new Date((startTime.getTime() + (12 + reservation.getTimeSlot()[0] * 2 * 3600000))); //attempt to add reservation time to the date
		
		newRes.setReservationTime(startTime);
		newRes.setDuration(reservation.getTimeSlot().length * 2 * 3600000);
		
		lookup.create(newRes);
	
		return "tableCheckout";
	}
	public String getTestValue() {
		return testValue;
	}
	public void setTestValue(String testvalue) {
		this.testValue = testvalue;
	}
	
	public String getSessionID() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().toString();
	}
}
