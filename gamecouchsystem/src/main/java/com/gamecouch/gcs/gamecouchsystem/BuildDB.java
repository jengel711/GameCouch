package com.gamecouch.gcs.gamecouchsystem;
import org.hibernate.*;

public class BuildDB {
	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			Location location = new Location("Main", State.NEW_YORK);
			session.save(location);
			session.save(new Customer("Alanbolte", "abolte@example.com", "password", location));
			session.save(new Customer("Captain Nemo", "cptnemo@example.com", "test123", location));
			location = new Location ("Columbus", State.OHIO);
			session.save(location);
			session.save(new Customer("John Smith", "jsmith@example.com", "deadbeef", location));
			session.getTransaction().commit();

		}
		catch (Exception e) {
		}
	}

}
