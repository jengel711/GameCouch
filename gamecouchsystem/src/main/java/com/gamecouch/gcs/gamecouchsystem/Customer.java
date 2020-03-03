package com.gamecouch.gcs.gamecouchsystem;

import java.security.GeneralSecurityException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;
import org.hibernate.type.DateType;

@ManagedBean(name = "customer")
@RequestScoped
@Entity
@Table
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@NaturalId(mutable = true)
	private String email; // add index?

	private byte[] password;
	private int loginAttempts = 0;
	
	@ManyToOne
	private Location defaultLocation;

	public Customer() {
	}

	public Customer(String name, String email, String password, Location location)  { 
		this.name = name;
		this.email = email;
		setPassword(password);
		this.defaultLocation = location;
	}

	public Customer(String name) {

		this.name = name;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(String password) { 
		var passwordHash = new Password(password);
		this.password = passwordHash.getPassword();
	}

	public boolean verifyPassword(String input) throws GeneralSecurityException {
		var storedPassword = new Password(password);
		boolean result = storedPassword.verifyPassword(input);
		if (result) {
			loginAttempts = 0;
		}			
		else {
			setLoginAttempts(loginAttempts + 1); //doesn't work, needs database support
		}
			
		return result;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.strip();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.strip();// validation?
	}

	public Location getDefaultLocation() {
		return defaultLocation;
	}

	public void setDefaultLocation(Location location) {
		this.defaultLocation = location;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", defaultLocation=" + defaultLocation.toString() + "]";
	}

	public String create() { // need to access this from a controller with validation (what?) and a success
								// page that links to the login page
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			session.save(this);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Login";
	}

}