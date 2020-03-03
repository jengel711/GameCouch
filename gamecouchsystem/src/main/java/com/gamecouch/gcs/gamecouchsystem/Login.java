package com.gamecouch.gcs.gamecouchsystem;

import java.security.GeneralSecurityException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name="login")
@RequestScoped
public class Login {

	private String email;
	private String password;
	private Customer customer;

	private static final long MAX_WAIT = 60 * 1000L; //seconds
	
	public Login() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String identifier) {
		this.email = identifier.strip();
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
			if (attempts > 0) {
				long wait = attempts * 5 * 1000L;
				Date waitingPeriod = new Date((wait < MAX_WAIT) ? wait : MAX_WAIT);  //Wait increases by 5 seconds per attempt
				if (waitingPeriod.after(new Date()))
					return showError("Please wait " + wait/1000 + " seconds and try again.");
			}
				
			try {
				validate = customer.verifyPassword(password);
			}
			catch (GeneralSecurityException e) {
				return showError("Sever security error.");
			}
		}
		
		if (validate)
			return "Profile";
		else {
			return showError("Unknown username or bad password.");
		}
	}
	
	private String showError(String error) {
		FacesMessage message = new FacesMessage(error);
		FacesContext.getCurrentInstance().addMessage("form:message", message);
		return "Login";
	}

}
