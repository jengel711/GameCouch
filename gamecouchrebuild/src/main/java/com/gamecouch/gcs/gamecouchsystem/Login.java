package com.gamecouch.gcs.gamecouchsystem;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
/**
 * @author Alan Bolte
 *
 */
@ManagedBean(name="login")
@SessionScoped
public class Login implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password; //vulnerability: we're keeping plaintext password in session
	private Customer customer;

	private static final long MAX_WAIT = 60 * 1000L; //seconds
	private String testString = "test 123";
	
	public Login() {

		System.out.println("Login constructor");
	}

	public String getEmail() {
		System.out.println("get" + email);
		return email;
	}

	public void setEmail(String identifier) {
		this.email = identifier.strip();
		System.out.println("set" + email);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.strip();
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public String doLogin( ) {
		boolean validate = false;
		var lookup = new Lookup();
		customer = lookup.getCustomerByEmail(email);
		if (customer != null) {
			int attempts = customer.getLoginAttempts();
			Date lastAttempt = customer.getLastAttempt(); //might be null, but only if attempts = 0.
			Date now = new Date();
			if (attempts > 0) {
				long wait = attempts * 5 * 1000L;
				Date waitingPeriod = new Date(lastAttempt.getTime()+((wait < MAX_WAIT) ? wait : MAX_WAIT));  //Wait increases by 5 seconds per attempt
				if (waitingPeriod.after(now))
					return showError("Please wait " + wait/1000 + " seconds and try again.");//message isn't great
			}
				
			try {
				validate = customer.verifyPassword(password, lookup);
			}
			catch (GeneralSecurityException e) {
				return showError("Sever security error.");
			}
		}
		
		if (validate)
			return "tableReservation"; //TODO: figure out how to get back to any page
		else {
			return showError("Unknown username or bad password.");
		}
	}
	
	private String showError(String error) {
		FacesMessage message = new FacesMessage(error);
		FacesContext.getCurrentInstance().addMessage("form:message", message);
		return "Login";
	}
	
	public String nameIfLoggedIn() {
		if (customer == null) {
			return "Welcome to GameCouch";
		}
		else
			return ("Hello, " + customer.getName());
			
	}
	
	public String link() {
		if (customer == null) {
			return "Log In/Sign Up";
		}
		else
			return "Log Out";
	}

	public String loginLogout() {
		if (customer == null) {
			return "Login";
		}
		else {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "tableReservation?faces-redirect=true";
		}
			
	}

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
}
