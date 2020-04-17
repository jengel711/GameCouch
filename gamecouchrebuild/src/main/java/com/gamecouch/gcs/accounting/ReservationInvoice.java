/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.time.LocalDate;

import javax.persistence.Entity;

/**
 * @author Alan Bolte
 *
 */

@Entity
public class ReservationInvoice extends Invoice {
	//private Reservation reservation;
	
	public ReservationInvoice() {
		super();
	}

	public ReservationInvoice(LocalDate dueDate, JournalEntry journalEntry) {
		super(dueDate, journalEntry);
	}
	
	
	
}
