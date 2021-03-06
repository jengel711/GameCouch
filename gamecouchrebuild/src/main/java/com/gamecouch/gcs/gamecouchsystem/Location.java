package com.gamecouch.gcs.gamecouchsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.*;
 
/**
 * @author Alan Bolte
 *
 */
@ManagedBean(name="location")
@ViewScoped
@Entity
@Table
public class Location implements PersistedData,Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 6135572819648433364L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    private String address;
    private String phone;
    
    //private List<com.gamecouch.gcs.reservation.PhysicalTable> tables; cannot determine type
    
    private Date hoursStart;
    private Date hoursEnd;
    
    @Enumerated(EnumType.STRING)
    private State state;
    
    @Transient
    private static List<Location> locations; 
    
    public Location() {
    	
    }
    
    public Location(String name, State state) {
        this.name = name;
        this.state = state;
    }
      

	@OneToMany(mappedBy="defaultLocation",cascade=CascadeType.PERSIST)
    private List<Customer> customers = new ArrayList<Customer>();
      

    public long getId() {
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
    
    public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
    
    public List<Customer> getCustomers() {
        return customers;
    }
    public void setEmployees(List<Customer> customers) {
        this.customers = customers;
    }
    
    public static Location findById(long id) {
    	var lookup = new Lookup();
    	return lookup.getLocationByID(Long.valueOf(id));
    }
    
    @SuppressWarnings("unchecked") //is this really a good idea? Not sure if getTable is designed well
	public static List<Location> getLocations() {
    	if (locations == null) {
    		var lookup = new Lookup();
    		var c = Location.class;
    		locations = (List<Location>) lookup.getTable(c); 
    	}
    		
    	return locations;
    }
    
    public String getNameAndState() {
    	return (name + ", "+ state);
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
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
/*
	public List<com.gamecouch.gcs.reservation.PhysicalTable> getTables() {
		return tables;
	}

	public void setTables(List<com.gamecouch.gcs.reservation.PhysicalTable> tables) {
		this.tables = tables;
	}
*/
	public Date getHoursStart() {
		return hoursStart;
	}

	public void setHoursStart(Date hoursStart) {
		this.hoursStart = hoursStart;
	}

	public Date getHoursEnd() {
		return hoursEnd;
	}

	public void setHoursEnd(Date hoursEnd) {
		this.hoursEnd = hoursEnd;
	}

}