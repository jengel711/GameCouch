package com.gamecouch.gcs.gamecouchsystem;



import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
//Current pattern holds open a permanent session for lookups. Is this a good design?
//seems to throw some errors regarding memory leak and connection reset in Tomcat console?

/**
 * Fetches information from the database.
 * 
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
	
	public Object getRowObjectByID(Class<?> objectClass, long id) {
		return session.get(objectClass, id);
	}
	
	//TODO: database-agnostic implementation
	public long getNextID(Class<?> objectClass) { 
		String sqlString = "SELECT IDENT_CURRENT('" + objectClass.getSimpleName() + "')";
		List<Object> result = session.createNativeQuery(sqlString).getResultList();
		BigDecimal currentId = (BigDecimal) result.get(0);
		return currentId.longValue() + 1;
	}
	
	public Customer getCustomerByEmail(String email) {
		return session.bySimpleNaturalId(Customer.class).load(email);
	}
	
    public Location getLocationByID(Long id) {
		return session.get(Location.class, id);
	}
    
    public List<? extends PersistedData> getTable(Class<? extends PersistedData> table) {
    	session.beginTransaction();
    	List<? extends PersistedData> rows = session.createQuery("from " + table.getSimpleName(), table).list();
    	session.getTransaction().commit();
    	return rows;
    }
    
    private static void initSession() {
    	session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public void create(PersistedData entity) {//would this be better in a different class?
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
    }
    
    public List<Object> cogs() {
    	String sqlString = "SELECT TOP (1000) [id]\r\n" + 
    			"      ,[credit]\r\n" + 
    			"	  ,[account_accountNumber]\r\n" + 
    			"\r\n" + 
    			"  FROM [gamecouch].[dbo].[JournalLine]\r\n" + 
    			"  WHERE credit IS NOT NULL\r\n" + 
    			"  AND account_accountNumber = 10156";
		return session.createNativeQuery(sqlString).getResultList();
    }
    
}
