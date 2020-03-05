/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.util.Date;

/**
 * @author Alan Bolte
 *
 */
public abstract class Invoice {
	private long id;
	private Date dueDate;
	private JournalEntry journalEntry;
	
	public Invoice(long id, Date dueDate, JournalEntry journalEntry) {
		super();
		this.id = id;
		this.dueDate = dueDate;
		this.journalEntry = journalEntry;
	}
	
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public JournalEntry getJournalEntry() {
		return journalEntry;
	}
	public void setJournalEntry(JournalEntry journalEntry) {
		this.journalEntry = journalEntry;
	}
}
