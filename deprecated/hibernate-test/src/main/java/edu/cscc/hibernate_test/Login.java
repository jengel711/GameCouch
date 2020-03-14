package edu.cscc.hibernate_test;

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
			validate = customer.verifyPassword(password);
		}
		
		if (validate)
			return "Profile";
		else {
			FacesMessage message = new FacesMessage("Unknown username or bad password.");
			FacesContext.getCurrentInstance().addMessage("form:message", message);
			return "Login";
		}
	}

}
