package com.gamecouch.gcs.accounting;

import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;

import org.junit.Test;

import com.gamecouch.gcs.gamecouchsystem.Lookup;

public class GetAccountByNumber {
	
	@Test
	public void getAccount() {
		long submittedValue = 10112;
		Object result;
		
        try {
        	var lookup = new Lookup();
        	result = lookup.getRowObjectByID(Account.class, Long.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Account ID"), e);
        }
        Account account = (Account) result;
        assert (submittedValue == account.getAccountNumber());
	}
	
	
}
