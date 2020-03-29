/**
 * 
 */
package com.gamecouch.gcs.accounting;



import java.util.ArrayList;
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
	private int lineQuantity;
	
	public LineCollectionBean( ) {
		lines = new ArrayList<JournalLine>(List.of(new JournalLine(1), new JournalLine(2)));
		lineQuantity = 2;
		
	}

	public List<JournalLine> getLines() {
		return lines;
	}

	public void setLines(List<JournalLine> lines) {
		this.lines = lines;
	} 
	

	public String addLine() {
		final JournalLine newLine = new JournalLine(++lineQuantity);
		lines.add(newLine);
		return "NewJournalEntry";
	}
	
	public String delete(JournalLine l) {
		lines.remove(l);
		--lineQuantity;
		
		int count = 0;
		for (JournalLine line : lines) {
			line.setLineNumber(++count);
		}
		return "NewJournalEntry";
	}


	
}
