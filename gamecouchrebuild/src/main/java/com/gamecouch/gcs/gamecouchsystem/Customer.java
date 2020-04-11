package com.gamecouch.gcs.gamecouchsystem;

import java.security.GeneralSecurityException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;
import org.hibernate.type.DateType;

/**
 * @author Alan Bolte
 *
 */
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
	private String email; //TODO: add index?

	private byte[] password;
	private int loginAttempts = 0;
	private Date lastAttempt;
	private String address;
	private String phone;
	private Date created;
	
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

	public String getPassword() {
		if (password == null)
			return "";
		return new String(password);
	}

	public void setPassword(String password) { 
		var passwordHash = new Password(password);
		this.password = passwordHash.getPassword();
	}

	public boolean verifyPassword(String input, Lookup lookup) throws GeneralSecurityException {
		var storedPassword = new Password(password);
		boolean result = storedPassword.verifyPassword(input);
		lookup.getSession().beginTransaction();
		if (result) {
			loginAttempts = 0;
		}			
		else {
			setLoginAttempts(loginAttempts + 1); 
			lastAttempt = new Date();
		}
		lookup.getSession().getTransaction().commit();
		return result;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Date getLastAttempt() {
		return lastAttempt;
	}

	public void setLastAttempt(Date lastAttempt) {
		this.lastAttempt = lastAttempt;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}