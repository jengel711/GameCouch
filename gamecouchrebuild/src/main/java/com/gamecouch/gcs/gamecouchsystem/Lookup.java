package com.gamecouch.gcs.gamecouchsystem;



import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
//Current pattern holds open a permanent session for lookups. Is this a good design?
//seems to throw some errors regarding memory leak and connection reset in Tomcat console?
import org.hibernate.criterion.Restrictions;

import com.gamecouch.gcs.accounting.JournalLine;

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
    
    public static void close() {
    	session.close();
    	session = null;
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
	
	public Object getRowObjectByStringField(Class<?> objectClass, String field, String value) {
		Criteria criteria = session.createCriteria(objectClass);
		return criteria.add(Restrictions.eq(field, value))
		                             .uniqueResult();
	}
	
	//TODO: database-agnostic implementation (low priority)
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
    
    @SuppressWarnings("unchecked")
	public List<JournalLine> cogs() { //I'm certain this should be in this class...
    	return session.createQuery(
    			"select l " +
    			"from JournalLine l " +
    			"where l.credit is not null ")    			
    			.getResultList();
    	
    	/*
    	 * "and l.account = :accountNumber", JournalLine.class)
    			.setParameter("accountNumber", 10156L)
    	 * 
    	String sqlString = "SELECT TOP (1000) [id],[credit],[account_accountNumber] FROM [dbo].[JournalLine] WHERE credit IS NOT NULL AND account_accountNumber = 10156";
		return session.createNativeQuery(sqlString).getResultList();*/
    }
    
}
