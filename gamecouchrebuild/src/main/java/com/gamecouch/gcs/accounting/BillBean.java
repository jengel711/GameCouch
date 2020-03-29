package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import com.gamecouch.gcs.gamecouchsystem.Lookup;

@ManagedBean(name = "bill")
public class BillBean {
	private long id;
	private Date invoiceDate = new Date();
	private Date dueDate = new Date();
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

		// get accounts
		// TODO: hard coded account numbers are bad
		Account cash = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(10102));
		Account payable = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(10201));
		Account inventory = (Account) lookup.getRowObjectByID(Account.class, Long.valueOf(10156));
		newBill.setVendor(bill.vendor);
		
		// create journal lines for entry
		JournalLine line = new JournalLine(entry, 1, null, bill.getAmount(), cash, "Bill ");
		lookup.create(line);
		line = new JournalLine(entry, 2, bill.getAmount(), null, payable, bill.getDescription());
		lookup.create(line);

		if (bill.isInventory()) {
			line = new JournalLine(entry, 3, bill.getAmount(), null, inventory, null);
			lookup.create(line);

		}

		// create bill
		newBill.setDueDate(LocalDate.ofInstant(bill.dueDate.toInstant(), ZoneId.systemDefault()));
		
		lookup.create(newBill);

		return "AddBill"; // TODO: results page, with buttons to choose between making another entry or
							// navigating elsewhere.
	}
}
