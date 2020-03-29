package com.gamecouch.gcs.accounting;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculateAccount {

	@Test
	public void test() {
		var accounts = Account.getAccounts();
		for (Account a : accounts) {
			System.out.println(a.getName() + ": " + a.recalculateTotal());
		}
		
		fail("Not yet implemented");
	}

}
