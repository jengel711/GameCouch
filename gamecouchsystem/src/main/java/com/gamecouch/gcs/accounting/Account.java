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
@Table(appliesTo="Account")
public class Account {
	
	@Id
	private long accountNumber;//don't auto-generate
	private String Name;
	private BigDecimal cachedTotal = new BigDecimal(0);
	
	public Account(long accountNumber, String name) {
		super();
		this.accountNumber = accountNumber;
		Name = name;
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public BigDecimal getCachedTotal() {
		return cachedTotal;
	}
	public void setCachedTotal(BigDecimal cachedTotal) {
		this.cachedTotal = cachedTotal;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
//	public BigDecimal recalculateTotal() {
//		
//	}
}
