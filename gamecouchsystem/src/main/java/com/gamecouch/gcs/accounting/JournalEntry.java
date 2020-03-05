/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.util.Date;

import javax.persistence.Id;
import org.hibernate.annotations.Table;

/**
 * @author Alan Bolte
 *
 */


@Table(appliesTo="JournalEntry")
public class JournalEntry {
	
	@Id
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
}
