package edu.cscc.hibernate_test;

import org.testng.annotations.Test;

public class LoginTest {
	@Test
	public void tryLogin() {
		var loginObject = new Login();
		loginObject.setEmail("cptnemo@example.com");
		loginObject.setPassword("test123");
		
		loginObject.doLogin();
		assert (loginObject.getCustomer() != null);
		assert (loginObject.getCustomer().getName().equals("Captain Nemo"));
	}
}
	
