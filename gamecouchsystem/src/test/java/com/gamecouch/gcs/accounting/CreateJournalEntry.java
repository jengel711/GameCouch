package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class CreateJournalEntry {
	
	@Test
	public void cashMoney( ) {

		
		var cashAccount = new Account(1001, "cash");
		
		var entry1 = new JournalEntry();
		entry1.setId(1); //not necessary once we have database connectivity
		entry1.setDate(new Date()); //today
		
		var cashline = new JournalLine();
		cashline.setId(1); //not necessary once we have database connectivity
		cashline.setJournal(entry1);
		cashline.setLineNumber(1);
		cashline.setAccount(cashAccount);
		cashline.setCredit(new BigDecimal(10000));
		cashline.setDescription("Briefcase full of cash");
		
		var entry2 = new JournalEntry();
		entry2.setId(2);
		entry2.setDate(new Date());
		
		var candy = new JournalLine();
		candy.setId(2);
		candy.setJournal(entry2);
		candy.setLineNumber(1);
		candy.setAccount(cashAccount);
		candy.setDebit(new BigDecimal(1.25));
		candy.setDescription("Candy");
		
		
		//non-database implementation of Account.recalculateTotal()
		var lines = new ArrayList<JournalLine>(); //is this the right kind of collection?
		lines.add(cashline);
		lines.add(candy);
		
		var total = new BigDecimal(0);
		for (JournalLine line : lines) {
			if (line.getCredit() != null)
				total = total.add(line.getCredit());
			
			if (line.getDebit() != null)
				total = total.subtract(line.getDebit());			
		}
		
		cashAccount.setCachedTotal(total);
		
		//assertions
		assert (cashAccount.getCachedTotal().equals(new BigDecimal(10000.0-1.25)));
	}
	
}
