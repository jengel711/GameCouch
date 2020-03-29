/**
 * 
 */
package com.gamecouch.gcs.accounting;


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
	
	private String selectedEntry;

	public String getSelectedEntry() {
		return selectedEntry;
	}

	public void setSelectedEntry(String journalEntry) {
		this.selectedEntry = journalEntry;
	}

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
