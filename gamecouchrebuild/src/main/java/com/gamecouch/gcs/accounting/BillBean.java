package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.gamecouch.gcs.gamecouchsystem.Lookup;

@ManagedBean(name = "bill")
public class BillBean {
	private long id;
	private Date invoiceDate = new Date();
	private Date dueDate = new Date(); //TODO: validation - warning if date is in the past?
	private Vendor vendor;
	private Lookup lookup = new Lookup();
	private JournalEntry entry;

	private BigDecimal amount;
	private boolean inventory;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Lookup getLookup() {
		return lookup;
	}

	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

	public JournalEntry getEntry() {
		return entry;
	}

	public void setEntry(JournalEntry entry) {
		this.entry = entry;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isInventory() {
		return inventory;
	}

	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void getNextId() {
		if (id == 0)
			id = lookup.getNextID(Invoice.class);
	}

	public List<Vendor> getVendors() {
		var v = Vendor.class;
		return (List<Vendor>) lookup.getTable(v);
	}

	public List<Account> getAccounts() {
		return Account.getAccounts();
	}

	public String create(BillBean bill) {
		var newBill = new Bill();
		
		// create journalEntry
		entry = new JournalEntry();
		entry.setDate(bill.getInvoiceDate());
		entry.create();
		newBill.setJournalEntry(entry);

		// create journal lines for entry
		// TODO: hard coded account numbers are bad
		
		Account payable = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(10201));
		Account account2;
		
		if (bill.isInventory()) {
			account2 = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(10156)); //inventory
		}
		else {
			account2 = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(50000)); //expenses
		}
		JournalLine line = new JournalLine(entry, 1, null, bill.getAmount(), payable, "Bill");
		lookup.create(line);
		line = new JournalLine(entry, 2, bill.getAmount(), null, account2, bill.getDescription());
		lookup.create(line);

		// create bill

		newBill.setVendor(bill.vendor);
		newBill.setDueDate(LocalDate.ofInstant(bill.dueDate.toInstant(), ZoneId.systemDefault()));
		
		lookup.create(newBill);
		
		FacesContext.getCurrentInstance().addMessage("NewBill", new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Created invoice, ID: " + newBill.getId()));

		return "AddBillResult"; // TODO: results page, with buttons to choose between making another entry or
							// navigating elsewhere.
	}
}
