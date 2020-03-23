/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;


/**
 * @author Alan Bolte
 *
 */

@Entity
public class Bill extends Invoice {
	
	@ManyToOne
	private Vendor vendor;
	//private InventoryCollection inventory
	
	public Bill() {
		super();
	}

	public Bill(LocalDate dueDate, JournalEntry journalEntry, Vendor vendor) {
		super(dueDate, journalEntry);
		this.setVendor(vendor);
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
}
