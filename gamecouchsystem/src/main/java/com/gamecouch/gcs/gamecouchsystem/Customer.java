package com.gamecouch.gcs.gamecouchsystem;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;
  

@ManagedBean(name="customer")
@RequestScoped
@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
    
    @NaturalId(mutable=true)
    private String email; //add index?
    
    private String password;

	@ManyToOne
    private Location defaultLocation;
 
    public Customer() {}
 
    public Customer(String name, String email, String password, Location location) { //currently used only by buildDB?
        this.name = name;
        this.email = email;
        this.password = password;
        this.defaultLocation = location;
    }     
 
    public Customer(String name) {
    	
        this.name = name;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) { //add hash function with dynamic salt
		this.password = password.strip();
	}
	
	public boolean verifyPassword(String password) { //update to match setPassword
		String input = password.strip();
		String stored = getPassword();
		return input.equals(stored);
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
		this.email = email.strip();//validation?
	}

	public Location getDefaultLocation() {
        return defaultLocation;
    }
 
    public void setDefaultLocation(Location location) {
        this.defaultLocation = location;
    }
 
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", defaultLocation="
                + defaultLocation.toString() + "]";
    }
    
    public String create( ) { //need to access this from a controller with validation (what?) and a success page that links to the login page
    	try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			session.save(this);
			session.getTransaction().commit();
		}
		catch (Exception e) {
		}
    	return "Login";
    }
 
}