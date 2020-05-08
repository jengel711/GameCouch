/**
 * 
 */
package com.gamecouch.gcs.accounting;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.gamecouch.gcs.gamecouchsystem.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author Alan Bolte
 *
 */

@ManagedBean(name="journal")
@RequestScoped
public class JournalBean {
	private long id;
	private Date date;
	
	private Lookup lookup = new Lookup();
	private JournalEntry entry;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {	
		if (id == 0 || id == lookup.getNextID(JournalEntry.class)) {
			date = new Date();
			return date;
		}
			
		if (date != null)
			return date;
					
		
		entry = (JournalEntry) lookup.getRowObjectByID(JournalEntry.class, id);
		date = entry.getDate();
		return  date; 
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
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
		boolean valid = validateJournal(journal, lineBean);
		
		if(valid) {
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
			
			
			FacesContext.getCurrentInstance().addMessage("NewJournal", new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Created journal entry " + entry.getId()));
			
		}
		

		lineBean.init();
		
		return "NewJournalEntryResult";
	}
	
	private boolean validateJournal(JournalBean journal, LineCollectionBean lineBean) {
		//TODO: refactor to deal with nulls
		/*
		for (JournalLine line : lineBean.getLines()) {	
			if (line.getCredit().doubleValue() < 0.0 || line.getDebit().doubleValue() < 0.0) {
				FacesContext.getCurrentInstance().addMessage("NewJournal", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Values must be positive"));
				return false;
			}	
			
				
		}
		
		BigDecimal credit = new BigDecimal(0);
		BigDecimal debit = new BigDecimal(0);
		
		for (JournalLine line : lineBean.getLines()) {	
			credit.add(line.getCredit());		
			debit.add(line.getDebit());
			
		}	
			
		if (credit.doubleValue() != debit.doubleValue()) {
			FacesContext.getCurrentInstance().addMessage("NewJournal", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Total credits must equal total debits."));
			return false;
		}
		*/
		
		
		return true;
	}
	
}
