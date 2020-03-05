/**
 * 
 */
package com.gamecouch.gcs.accounting;

import java.util.Date;

import javax.persistence.*;
/**
 * @author Alan Bolte
 *
 */


@Entity
public class JournalEntry {
	
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
}
