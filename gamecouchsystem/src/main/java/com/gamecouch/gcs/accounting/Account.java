/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.Location;
import com.gamecouch.gcs.gamecouchsystem.Lookup;
import com.gamecouch.gcs.gamecouchsystem.PersistedData;


/**
 * @author Alan Bolte
 *
 */
@Entity
public class Account implements PersistedData {
	
	@Id
	private long accountNumber; //doesn't auto-generate, so buildDB must set the account numbers
	private String name;
	private BigDecimal cachedTotal = new BigDecimal(0);
	private static List<Account> accounts;
	
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
	
    @SuppressWarnings("unchecked") //is this really a good idea? Not sure if getTable is designed well
	public static List<Account> getAccounts() {
    	if (accounts == null) {
    		var lookup = new Lookup();
    		var c = Account.class;
    		accounts = (List<Account>) lookup.getTable(c); 
    	}
    		
    	return accounts;
    }
	
//	public BigDecimal recalculateTotal() {
//		
//	}
}
