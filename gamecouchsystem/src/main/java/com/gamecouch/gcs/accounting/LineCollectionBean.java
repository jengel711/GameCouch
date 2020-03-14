/**
 * 
 */
package com.gamecouch.gcs.accounting;



import java.util.List;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;


/**
 * @author Alan Bolte
 *
 */

@ManagedBean(name="lineCollection")
@SessionScoped
public class LineCollectionBean {
	private List<JournalLine> lines;

	public List<JournalLine> getLines() {
		return lines;
	}

	public void setLines(List<JournalLine> lines) {
		this.lines = lines;
	} 
	




	
}
