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
	private boolean asset; //DEAL: Dividends, Expenses, Assets, Losses
	
	public Account() {
		super();
	}
	
	public Account(long accountNumber, String name, boolean isAsset) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.asset = isAsset;
	}
	
	public Account(long accountNumber, String name, boolean isAsset, BigDecimal cachedTotal) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.cachedTotal = cachedTotal;
		this.asset = isAsset;
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
	
    public boolean isAsset() {
		return asset;
	}

	public void setAsset(boolean isAsset) {
		this.asset = isAsset;
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
    
    @Override
    public String toString( ) {
    	return String.valueOf(accountNumber);
    }

	public BigDecimal recalculateTotal() {
		var lookup = new Lookup();
		var journals = lookup.getTable(JournalLine.class);
		
		for (Object j : journals) {			
			JournalLine line = (JournalLine) j;
			if (line.getAccount() != this) //TODO: replace with SQL Where query or simliar hibernate feature 
				continue;
			
			cachedTotal = BigDecimal.ZERO;
			//assumption: every line will be credit or debit. TODO: validation		
			if (line.getCredit() == null) { //if debit
				if (isAsset())
					cachedTotal = cachedTotal.add(line.getDebit()); //debits increase asset accounts
				else
					cachedTotal = cachedTotal.subtract(line.getDebit()); //liability account
			}			
			else { //if credit
				if (isAsset())
					cachedTotal = cachedTotal.subtract(line.getCredit()); //credits decrease asset accounts
				else
					cachedTotal = cachedTotal.add(line.getCredit());
			}
				
		}
		return cachedTotal;
	}
}
