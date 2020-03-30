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

@ManagedBean(name = "income")
public class IncomeReportBean {
	private Map<String, Account> allAccounts;

	private List<Account> revenuesList;
	private List<Account> expensesList;
	private double net;
	
	public IncomeReportBean() {
		allAccounts = new Hashtable<>(10);
		revenuesList = new ArrayList<>();
		expensesList = new ArrayList<>();
				
		List<Account> accountList = Account.getAccounts();
		for (Account a : accountList) {
			a.recalculateTotal(); //kind of a hack? Not sure this should be here.
			allAccounts.put(a.getName(), a);
		}
		
		Account revenue = allAccounts.get("Revenues");
		
		
		//TODO: All of these should be changes over the period
		var interest = new Account(0,"Interest revenues",false,new BigDecimal(300));
		var gain = new Account(0,"Gain on sales of assets",false,new BigDecimal(2000)); 
		BigDecimal totalRev = revenue.getCachedTotal().add(interest.getCachedTotal()).add(gain.getCachedTotal());
		var totalRevAcct = new Account(0," Total revenue & gains",false,totalRev); 
		
		revenuesList.add(revenue);
		revenuesList.add(interest);
		revenuesList.add(gain);
		revenuesList.add(totalRevAcct);
		
		
		Account expenses = allAccounts.get("Expenses");
		

		var cogs = new Account(0,"Cost of goods sold",false,BigDecimal.ZERO);
		var lookup = new Lookup();
		var cogsList = lookup.cogs();
		for (Object o : cogsList)
		{
			JournalLine line = (JournalLine) o;
			var increase = cogs.getCachedTotal().add(line.getCredit());
			cogs.setCachedTotal(increase); 
		}
		
		expensesList.add(cogs);
		//expenses.setName("Operating expense"); this unexpectedly changes the database, need to find a way to make it just change the view, or maybe use javascript for that...
		expensesList.add(expenses);
		var interestExpense = new Account(0,"Interest expense",false,new BigDecimal(500));
		expensesList.add(interestExpense);
		var loss = new Account(0,"Loss",false,new BigDecimal(1500));
		expensesList.add(loss);
		BigDecimal totalExp = cogs.getCachedTotal().add(expenses.getCachedTotal()).add(interestExpense.getCachedTotal()).add(loss.getCachedTotal());
		var totalExpAcct = new Account(0," Total expenses & losses",false,totalExp); 
		expensesList.add(totalExpAcct);
		
		//calculation of total
		BigDecimal netIncome = totalRev.subtract(totalExp);
		net = netIncome.doubleValue();
	}

	public Map<String, Account> getAllAccounts() {
		return allAccounts;
	}

	public void setAllAccounts(Map<String, Account> allAccounts) {
		this.allAccounts = allAccounts;
	}

	public List<Account> getRevenuesList() {
		return revenuesList;
	}

	public void setRevenuesList(List<Account> additions) {
		this.revenuesList = additions;
	}

	public List<Account> getExpensesList() {
		return expensesList;
	}

	public void setExpensesList(List<Account> subtractions) {
		this.expensesList = subtractions;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double operations) {
		this.net = operations;
	}
}
