package com.gamecouch.gcs.gamecouchsystem;



import java.util.List;

import org.hibernate.Session;
//Current pattern holds open a permanent session for lookups. Is this a good design?
//seems to throw some errors regarding memory leak and connection reset in Tomcat console?

/**
 * @author Alan Bolte
 *
 */
public class Lookup {
	private static Session session;
	
    public Lookup() {
    	if (session == null)
    		initSession();
    }
    
    public void close() {
    	session.close();
    }
    
    public Session getSession( ) {
    	return session;
    }
    
	public Customer getCustomerByID(Long id) {
		return session.get(Customer.class, id);
	}
	
	public Customer getCustomerByEmail(String email) {
		return session.bySimpleNaturalId(Customer.class).load(email);
	}
	
    public Location getLocationByID(Long id) {
		return session.get(Location.class, id);
	}
    
    public List<Location> getLocations() {
    	session.beginTransaction();
    	List<Location> locations = session.createQuery("from Location", Location.class).list();
    	session.getTransaction().commit();
    	return locations;
    }
    
    private static void initSession() {
    	session = HibernateUtil.getSessionFactory().openSession();
    }
    

}
