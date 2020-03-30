package com.gamecouch.gcs.accounting;


import javax.faces.bean.ManagedBean;


@ManagedBean(name = "accounting")
public class AccountingController {
	private int report;
	
	
	
	public String viewReport() {
		switch (report) {
		case 1:
			return "BalanceSheet";
		case 2: 
			return "Cashflow";
		default:
			return "IncomeReport";
		}
	}



	public int getReport() {
		return report;
	}



	public void setReport(int report) {
		this.report = report;
	}
	
}
