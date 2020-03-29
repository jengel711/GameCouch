/**
 * 
 */
package com.gamecouch.gcs.accounting;


import java.math.BigDecimal;
import java.util.List;
import com.gamecouch.gcs.gamecouchsystem.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author Alan Bolte
 *
 */

@ManagedBean(name="viewJournal")
@ViewScoped
public class ViewJournalBean {
	private Long journalId;
	private JournalEntry journal;
	private List<JournalLine> lines;
	
	private Lookup lookup = new Lookup();

    public void init() {
        if (journalId == null) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        journal = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, journalId);

        if (journal == null) {
            String message = "Bad request. Unknown entry.";
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        else {
        	lines = journal.getLines();
        }
    }
    
	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}
	


	public JournalEntry getJournal() {
		return journal;
	}


	public void setJournal(JournalEntry journal) {
		this.journal = journal;
	}







	public Lookup getLookup() {
		return lookup;
	}


	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}


	public List<Account> getAccounts() {
		return Account.getAccounts();
	}
	
}
