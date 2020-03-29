/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.PersistedData;

/**
 * @author Alan Bolte
 *
 */

@Entity
@Inheritance
public abstract class Invoice implements PersistedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate dueDate;
	
	@ManyToOne
	private JournalEntry journalEntry;
	
	public Invoice(LocalDate dueDate, JournalEntry journalEntry) {
		super();
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
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public JournalEntry getJournalEntry() {
		return journalEntry;
	}
	public void setJournalEntry(JournalEntry journalEntry) {
		this.journalEntry = journalEntry;
	}
	
	
	
	//abstract Date getZonedDueDate();
	//might want to add this later
}
