/**
 * 
 */
package com.gamecouch.gcs.accounting;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.gamecouch.gcs.gamecouchsystem.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Alan Bolte
 *
 */

@ManagedBean(name="journal")
@RequestScoped
public class JournalBean {
	private long id;
	private Date date;
	//private List<JournalLine> lines;
	//private int lineQuantity;
	
	
	private Lookup lookup = new Lookup();
	private JournalEntry entry;
	
	public long getId() {
		System.out.println("journalbean getId: " + id);
		return id;
	}
	
	public void setId(long id) {
		System.out.println("journalbean setId, original: " + this.id + " new: " + id );
		this.id = id;
	}
	public Date getDate() {	
		System.out.println("journalbean getDate");
		if (id == 0 || id == lookup.getNextID(JournalEntry.class)) {
			System.out.println("new date for id:" + id);			
			date = new Date();
			return date;
		}
			
		if (date != null)
			return date;
					
		
		System.out.println("before lookup: " + (entry == null ? "no entry" : entry.toString()));
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id);
		System.out.println("after lookup: " + (entry == null ? "no entry" : entry.toString()));
		date = entry.getDate();
		return  date; 
	}
	
	public void setDate(Date date) {
		System.out.println("journalbean setDate");
		this.date = date;
	}
	
	/*public List<JournalLine> getLines() {
		System.out.println("journalbean getLines");
		if (id == 0 || id == lookup.getNextID(JournalEntry.class)) { 
			System.out.println("new lines for id:" + id);
			if (lines == null) {
				lines = new ArrayList<JournalLine>(List.of(new JournalLine(1), new JournalLine(2)));
				lineQuantity = 2;
			}
			return lines;
		}
		
		if (lines != null)
			return lines;
		
		System.out.println("lines lookup for id:" + id);
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id); //TODO: remove duplication
		return entry.getLines();
	}
	
	public void setLines(List<JournalLine> lines) {//TODO
		System.out.println("journalbean getLines");
		this.lines = lines;
	}*/
	
	public String shortDate() { //TODO: unnecessary duplication?
		
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		return formatter.format(getDate());
	}
	
	public List<Account> getAccounts() {
		return Account.getAccounts();
	}
	
	public void getNextId() {
		if (id == 0)
			id = lookup.getNextID(JournalEntry.class);
	}
	
	public String create(JournalBean journal, LineCollectionBean lineBean) {
		System.out.println("journalbean create");
		if (entry == null) {
			entry = new JournalEntry();
			entry.setId(journal.getId());
			entry.setDate(journal.getDate());			
		}
		
		entry.create();
		
		for (JournalLine line : lineBean.getLines()) {	
			line.setJournal(entry);
			lookup.create(line);
		}
		
		return "NewJournalEntry";
	}
	

	
}
