/**
 * 
 */
package com.gamecouch.gcs.accounting;


import java.text.SimpleDateFormat;
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
	private List<JournalLine> lines;
	
	
	private Lookup lookup = new Lookup();
	private JournalEntry entry;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {	
		if (id == lookup.getNextID(JournalEntry.class))
			return new Date();
			
		if (date != null)
			return date;
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id);
		this.date = entry.getDate();
		return  date; 
	}
	
	public void setDate(Date date) {//TODO
		this.date = date;
	}
	public List<JournalLine> getLines() {
		if (lines != null)
			return lines;
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id); //TODO: remove duplication
		return entry.getLines();
	}
	public void setLines(List<JournalLine> lines) {//TODO
		this.lines = lines;
	}
	
	public String shortDate() { //TODO: unnecessary duplication?
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		return formatter.format(getDate());
	}
	
	public void getNextId() {
		if (id == 0)
			id = lookup.getNextID(JournalEntry.class);
	}
	
	public void create() {
		entry.create();
	}
	
}
