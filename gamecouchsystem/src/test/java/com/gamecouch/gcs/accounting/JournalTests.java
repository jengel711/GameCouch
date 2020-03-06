package com.gamecouch.gcs.accounting;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;

import com.gamecouch.gcs.gamecouchsystem.*;


public class JournalTests {
/*
	@Test
	public void createEmptyEntry() {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			var entry = new JournalEntry();
			entry.setDate(new Date());
			session.save(entry);

			session.getTransaction().commit();

		}
		catch (Exception e) {
			assert false;
		}
		
		assert true;
		// would it be better to check we can get the row?
		// delete row after we're done? How to deal with row id?
	}
	*/
	@Test
	public void createEntryWithLines( ) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			var entry = new JournalEntry();
			entry.setDate(new Date());
			session.save(entry);
			
			var lookup = new Lookup();
			Account cashAccount = (Account) lookup.getRowObjectByID(Account.class, 10102);
			Account inventoryAccount = (Account) lookup.getRowObjectByID(Account.class, 10156);
			
			var cashline = new JournalLine();

			cashline.setJournal(entry);
			cashline.setLineNumber(1);
			cashline.setAccount(cashAccount);
			cashline.setDebit(new BigDecimal(1.25));
			cashline.setDescription("Testing");
						
			var candy = new JournalLine();

			candy.setJournal(entry);
			candy.setLineNumber(2);
			candy.setAccount(inventoryAccount);
			candy.setCredit(new BigDecimal(1.25));
			candy.setDescription("Testing");
			
			session.save(cashline);
			session.save(candy);

			session.getTransaction().commit();

		}
		catch (Exception e) {
			assert false;
		}
		
		assert true;
	}
}
