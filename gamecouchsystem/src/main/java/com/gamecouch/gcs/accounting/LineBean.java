/**
 * 
 */
package com.gamecouch.gcs.accounting;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.gamecouch.gcs.gamecouchsystem.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.ManyToOne;

/**
 * @author Alan Bolte
 *
 */

@ManagedBean(name="line")
@RequestScoped
public class LineBean {
	private long id;
	
	private JournalEntry journal;
	private int lineNumber; 
	
	private Account account;
	private BigDecimal debit;
	private BigDecimal credit;
	private String description;
	
	private Lookup lookup = new Lookup();

	public LineBean() {
		lineNumber = 1;
		//this.account = (Account) lookup.getRowObjectByID(Account.class, 10102);	
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public JournalEntry getJournal() {
		return journal;
	}


	public void setJournal(JournalEntry journal) {
		this.journal = journal;
	}


	public int getLineNumber() {
		return lineNumber;
	}


	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public BigDecimal getDebit() {
		return debit;
	}


	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}


	public BigDecimal getCredit() {
		return credit;
	}


	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
	
	
	public String create(LineBean bean) {
		System.out.println("linebean create");
			var line = new JournalLine(journal, lineNumber, credit, debit, account, description);
		
		lookup.create(line);
		return "NewJournalEntry";
	}
	
}
