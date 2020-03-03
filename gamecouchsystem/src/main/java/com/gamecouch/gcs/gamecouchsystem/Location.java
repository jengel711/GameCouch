package com.gamecouch.gcs.gamecouchsystem;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.*;
 
@ManagedBean(name="location")
@ViewScoped
@Entity
@Table
public class Location {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
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
    
    public static List<Location> getLocations() {
    	if (locations == null) {
    		var lookup = new Lookup();
    		locations = lookup.getLocations();
    	}
    		
    	return locations;
    }
    
    public String getNameAndState() {
    	return (name + ", "+ state);
    }
    
    @Override
    public String toString() {
        return (id.toString());
    }

}