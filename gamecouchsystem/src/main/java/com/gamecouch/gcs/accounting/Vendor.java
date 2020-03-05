/**
 * 
 */
package com.gamecouch.gcs.accounting;

/**
 * @author Alan Bolte
 *
 */
public class Vendor {
	private long id;
	private String name;
	private String address; //could be decomposed.
	
	public Vendor() {
		super();
	}
	
	
	public Vendor(long id, String name, String address) {
		super();
		this.id = id;
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

}
