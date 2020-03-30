package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.gamecouch.gcs.gamecouchsystem.Lookup;

@ManagedBean(name = "balance")
public class BalanceReportBean {

	private LocalDate today;
	private Map<String, Account> allAccounts;
	private List<Account> assets;
	private List<Account> property;
	private List<Account> intangible;
	private double totalAssets;
	private List<Account> liabilities;
	private double totalLiabilities;
	private double equity;
			
	public BalanceReportBean ( ) {
		today = LocalDate.now();
		allAccounts = new Hashtable<>(10);
		assets = new ArrayList<>();
		property = new ArrayList<>();
		intangible = new ArrayList<>();
		liabilities = new ArrayList<>();
		
		List<Account> accountList = Account.getAccounts();
		for (Account a : accountList) {
			a.recalculateTotal(); //kind of a hack? Not sure this should be here.
			allAccounts.put(a.getName(), a);
		}
		
		//assets
		assets.add(allAccounts.get("Cash"));
		assets.add(allAccounts.get("Accounts Receivable"));
		assets.add(allAccounts.get("Inventory"));
		
		for (Account a : assets) {
			totalAssets += a.getCachedTotal().doubleValue();
		}
		
		//dummy values
		property.add(new Account(0,"Equipment",true,new BigDecimal(100000)));
		totalAssets += 100000;
		intangible.add(new Account(0,"Goodwill",true,new BigDecimal(50000)));
		totalAssets += 50000;
		
		//Liabilities
		liabilities.add(allAccounts.get("Accounts Payable"));
		liabilities.add(allAccounts.get("Unearned Revenues"));
		
		for (Account a : liabilities) {
			totalLiabilities += a.getCachedTotal().doubleValue();
		}
		
		equity = totalAssets - totalLiabilities;//Definitely a hack
		
	}
	
	public LocalDate getToday() {
		return today;
	}

	public void setToday(LocalDate today) {
		this.today = today;
	}

	public Map<String, Account> getAllAccounts() {
		return allAccounts;
	}

	public void setAllAccounts(Map<String, Account> allAccounts) {
		this.allAccounts = allAccounts;
	}

	public List<Account> getAssets() {
		return assets;
	}

	public void setAssets(List<Account> assets) {
		this.assets = assets;
	}

	public List<Account> getProperty() {
		return property;
	}

	public void setProperty(List<Account> property) {
		this.property = property;
	}

	public List<Account> getIntangible() {
		return intangible;
	}

	public void setIntangible(List<Account> intangible) {
		this.intangible = intangible;
	}

	public double getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public List<Account> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<Account> liabilities) {
		this.liabilities = liabilities;
	}

	public double getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public double getEquity() {
		return equity;
	}

	public void setEquity(double equity) {
		this.equity = equity;
	}

	
}
