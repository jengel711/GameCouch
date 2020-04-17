package com.gamecouch.gcs.reservation;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import com.gamecouch.gcs.accounting.ReservationInvoice;

import com.gamecouch.gcs.gamecouchsystem.*;

@Entity
public class Reservation implements PersistedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Location location;
	
	private Date reservationTime;
	private int duration;
	//TODO: inventory list
	private byte partySize;
	
	@ManyToOne
	private PhysicalTable table;//multiple tables at a later time?
	
	@OneToOne
	private ReservationInvoice invoice;
	
	public Reservation() {}
	
	public Reservation(Customer customer, Location location, Date reservationTime, int duration,
			byte partySize, PhysicalTable table, ReservationInvoice invoice) {
		super();
		this.customer = customer;
		this.location = location;
		this.reservationTime = reservationTime;
		this.duration = duration;
		this.partySize = partySize;
		
		var lookup = new Lookup();
				
		this.table = table;
		this.invoice = invoice;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Date getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public byte getPartySize() {
		return partySize;
	}
	public void setPartySize(byte partySize) {
		this.partySize = partySize;
	}
	
	public Date getEndTime () {
		return new Date(reservationTime.getTime() + duration);
	}
	public PhysicalTable getTable() {
		return table;
	}
	public void setTable(PhysicalTable table) {
		this.table = table;
	}
	public ReservationInvoice getInvoice() {
		return invoice;
	}
	public void setInvoice(ReservationInvoice invoice) {
		this.invoice = invoice;
	}
	

	
}
