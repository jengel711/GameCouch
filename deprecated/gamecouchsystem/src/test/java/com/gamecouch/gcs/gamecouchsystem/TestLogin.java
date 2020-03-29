package com.gamecouch.gcs.gamecouchsystem;

import org.junit.Test;

public class TestLogin {
	@Test
	public void tryLogin( ) {
		
		var login = new Login();
		login.setEmail("abolte@example.com");
		login.setPassword("x");
		login.doLogin();
	}
}
