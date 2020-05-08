/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;

import com.gamecouch.gcs.gamecouchsystem.HibernateUtil;
import com.gamecouch.gcs.gamecouchsystem.Lookup;

/**
 * @author Alan Bolte
 *
 */

@Entity
public class JournalEntry implements com.gamecouch.gcs.gamecouchsystem.PersistedData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date date;

	@OneToMany(mappedBy = "journal")
	private List<JournalLine> lines;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<JournalLine> getLines() {//TODO: this doesn't work. why?
		if (lines == null) {
			System.out.println("retrieving journal lines");
			var look = new Lookup();
			
			
			List<JournalLine> table = (List<JournalLine>) look.getTable(JournalLine.class);
			lines =  table; //TODO: needs optimization
			lines.clear(); 
			for (JournalLine line : table) {
				System.out.println(line.getId());
				if (line.getJournal() == this) {
					lines.add(line);
				}
			}
		}
			
			
		return lines;
	}

	public String shortDate() {
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		return formatter.format(getDate());
	}

	// variant from original design in Location. We can keep the list cached
	// elsewhere if we need it cached; this method goes to the db every time.
	@SuppressWarnings("unchecked")
	public static List<JournalEntry> getEntries() {
		var lookup = new Lookup();
		var c = JournalEntry.class;
		return (List<JournalEntry>) lookup.getTable(c);
	}

	public void create() { // need to access this from a controller with validation (what?) and a success
		
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			session.beginTransaction();
			session.save(this);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
