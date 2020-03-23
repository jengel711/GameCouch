/**
 * 
 */
package com.gamecouch.gcs.accounting;

/**
 * @author Alan Bolte
 *
 */

import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.*;

@Entity
public class Vendor implements PersistedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String address; //could be decomposed.
	
	public Vendor() {
	}
	
	
	public Vendor(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	//TODO: there has to be a better way to do this
    @Override
    public String toString( ) {
    	return String.valueOf(id);
    }
}
