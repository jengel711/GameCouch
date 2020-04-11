package com.gamecouch.gcs.reservation;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.gamecouch.gcs.gamecouchsystem.*;

@Entity
public class PhysicalTable implements PersistedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	byte seats;
	
	public PhysicalTable() {}
	

	public PhysicalTable(byte seats) {
		this.seats = seats;		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getSeats() {
		return seats;
	}

	public void setSeats(byte seats) {
		this.seats = seats;
	}	

	@SuppressWarnings("unchecked") //is this really a good idea? Not sure if getTable is designed well
	public static List<PhysicalTable> getTables() {
    	List<PhysicalTable> tables;
    		var lookup = new Lookup();
    		var c = PhysicalTable.class;
    		tables = (List<PhysicalTable>) lookup.getTable(c); 
    	
    		
    	return tables;
    }
}
