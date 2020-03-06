/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.Location;
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
	
	//variant from original design in Location. We can keep the list cached elsewhere if we need it cached; this method goes to the db every time.
    @SuppressWarnings("unchecked")
	public static List<JournalEntry> getEntries() {
    		var lookup = new Lookup();
    		var c = JournalEntry.class;
    		return (List<JournalEntry>) lookup.getTable(c); 
    }
}
