/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.util.Date;
import javax.persistence.Id;
import org.hibernate.annotations.Table;


/**
 * @author Alan Bolte
 *
 */

@Table(appliesTo="Bill")
public class Bill extends Invoice {
	private Vendor vendor;
	//private InventoryCollection inventory

	public Bill(long id, Date dueDate, JournalEntry journalEntry, Vendor vendor) {
		super(id, dueDate, journalEntry);
		this.setVendor(vendor);
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
}
