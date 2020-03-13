/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.Lookup;

/**
 * @author Alan Bolte
 *
 */

@Entity
public class JournalLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private JournalEntry journal;
	private int lineNumber; //needs validation
	
	@ManyToOne
	private Account account;
	private BigDecimal debit;
	private BigDecimal credit;
	private String description;
	
	public JournalLine() {}
	
	public JournalLine(JournalEntry journal, int lineNumber, double credit, double debit, Account account, String description) {
		this.journal = journal;
		this.lineNumber = lineNumber;
		this.account = account;
		this.credit = credit == 0 ? null : BigDecimal.valueOf(credit);
		this.debit = debit == 0 ? null : BigDecimal.valueOf(debit);
		this.account = account;
		this.description = description;
		
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
	public void setLineNumber(int lineNumber) { //validation idea: prevent duplicates
		this.lineNumber = lineNumber;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) { 
		this.account = account;
	}

	public void setAccount(long accountNumber) { 
		var lookup = new Lookup();
		this.account = (Account) lookup.getRowObjectByID(Account.class, accountNumber);		
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

}
