package edu.cscc.hibernate_test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
  

@ManagedBean(name="customer")
@RequestScoped
@Entity
@Table
public class Customer {
	private static final Logger logger = LogManager.getLogger(Customer.class.getName());
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
    private String password;

	@ManyToOne
    private Location defaultLocation;
 
    public Customer() {}
 
    public Customer(String name, String password, Location location) {
        this.name = name;
        this.password = password;
        this.defaultLocation = location;
    }     
 
    public Customer(String name) {
    	
        this.name = name;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
        this.name = name;
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
    
    public void create( ) {
    	try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			session.save(this);
			session.getTransaction().commit();
		}
		catch (Exception e) {
			logger.error("Failed to create customer", e); //$NON-NLS-1$
			
		}
    }
 
}