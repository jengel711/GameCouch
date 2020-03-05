/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import javax.persistence.Id;
import org.hibernate.annotations.Table;

/**
 * @author Alan Bolte
 *
 */

@Table(appliesTo="JournalLine")
public class JournalLine {
	@Id
	private long id;
	private JournalEntry journal;
	private int lineNumber; 	
	private Account account;
	private BigDecimal debit;
	private BigDecimal credit;
	private String description;
	
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
