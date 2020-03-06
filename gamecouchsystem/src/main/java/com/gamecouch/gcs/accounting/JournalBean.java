/**
 * 
 */
package com.gamecouch.gcs.accounting;

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
		if (date != null)
			return date;
		
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id); //cache this?
		this.date = entry.getDate();
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<JournalLine> getLines() {
		return lines;
	}
	public void setLines(List<JournalLine> lines) {
		this.lines = lines;
	}
	
	
}
