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

@ManagedBean(name="journalReport")
@RequestScoped
public class GeneralJournalBean {

	private List<JournalEntry> entries;

	public List<JournalEntry> getEntries() {
		
		if (entries != null)
			return entries;
		entries = JournalEntry.getEntries();
		
		return  entries; 
		
	}

	public void setEntries(List<JournalEntry> entries) {
		this.entries = entries;
	}
	
}