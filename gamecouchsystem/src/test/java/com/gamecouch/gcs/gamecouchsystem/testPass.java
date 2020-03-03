package com.gamecouch.gcs.gamecouchsystem;

import java.security.GeneralSecurityException;

import org.junit.Test;

public class testPass {

	@Test
	public void passwordVerification() {
		var passObj = new Password("password123");
		//System.out.println("Password stored as: " + new String (passObj.getPassword()));
		boolean success = false;
		try {
			success = passObj.verifyPassword("password123");
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert success;
	}

}
