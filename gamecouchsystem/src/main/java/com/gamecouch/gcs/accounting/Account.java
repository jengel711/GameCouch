/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;

import javax.persistence.*;


/**
 * @author Alan Bolte
 *
 */
@Entity
public class Account {
	
	@Id
	private long accountNumber; //doesn't auto-generate, so buildDB must set the account numbers
	private String name;
	private BigDecimal cachedTotal = new BigDecimal(0);
	
	public Account() {
		super();
	}
	
	public Account(long accountNumber, String name) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
	}
	
	public Account(long accountNumber, String name, BigDecimal cachedTotal) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.cachedTotal = cachedTotal;
	}
	
	
	
	public long getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getCachedTotal() {
		return cachedTotal;
	}
	public void setCachedTotal(BigDecimal cachedTotal) {
		this.cachedTotal = cachedTotal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public BigDecimal recalculateTotal() {
//		
//	}
}
