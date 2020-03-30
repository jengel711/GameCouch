package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

@ManagedBean(name = "cashflow")
public class CashflowReportBean {
	private Map<String, Account> allAccounts;
	private double income;
	private List<Account> additions;
	private List<Account> subtractions;
	private double operations;
	
	public CashflowReportBean() {
		allAccounts = new Hashtable<>(10);
		additions = new ArrayList<>();
		subtractions = new ArrayList<>();
				
		List<Account> accountList = Account.getAccounts();
		for (Account a : accountList) {
			a.recalculateTotal(); //kind of a hack? Not sure this should be here.
			allAccounts.put(a.getName(), a);
		}
		
		Account revenue = allAccounts.get("Revenues");
		double revenueValue = revenue.getCachedTotal().setScale(2, RoundingMode.HALF_UP).doubleValue();
		Account expenses = allAccounts.get("Expenses");
		income = revenueValue - expenses.getCachedTotal().setScale(2, RoundingMode.HALF_UP).doubleValue();//better to do the calculation as BigDecimal?
		
		//TODO: All of these should be changes over the period
		var depreciation = new Account(0,"Depreciation",false,new BigDecimal(1000));
		var taxes = new Account(0,"Deferred Taxes",false,new BigDecimal(revenueValue * 0.0675)); //more fakery for taxes
		
		additions.add(depreciation);
		additions.add(allAccounts.get("Accounts Payable"));
		additions.add(taxes); 
		
		subtractions.add(allAccounts.get("Accounts Receivable"));
		subtractions.add(allAccounts.get("Inventory"));
		
		//calculation of total
		BigDecimal opCash = BigDecimal.ZERO;
		opCash = opCash.add(BigDecimal.valueOf(income));
		opCash = opCash.add(depreciation.getCachedTotal());
		opCash = opCash.add(allAccounts.get("Accounts Payable").getCachedTotal());
		opCash = opCash.add(taxes.getCachedTotal());
		opCash = opCash.subtract(allAccounts.get("Accounts Receivable").getCachedTotal());
		opCash = opCash.subtract(allAccounts.get("Inventory").getCachedTotal());
		operations = opCash.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public Map<String, Account> getAllAccounts() {
		return allAccounts;
	}

	public void setAllAccounts(Map<String, Account> allAccounts) {
		this.allAccounts = allAccounts;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public List<Account> getAdditions() {
		return additions;
	}

	public void setAdditions(List<Account> additions) {
		this.additions = additions;
	}

	public List<Account> getSubtractions() {
		return subtractions;
	}

	public void setSubtractions(List<Account> subtractions) {
		this.subtractions = subtractions;
	}

	public double getOperations() {
		return operations;
	}

	public void setOperations(double operations) {
		this.operations = operations;
	}
}
