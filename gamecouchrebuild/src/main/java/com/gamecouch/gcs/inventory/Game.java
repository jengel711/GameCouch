package com.gamecouch.gcs.inventory;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import com.gamecouch.gcs.gamecouchsystem.PersistedData;

@Entity
public class Game implements PersistedData{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String description;
	private String sku;
	private BigDecimal price;
	private boolean accessory;
	
	@ManyToOne
	private Game expansionFor;
	private LocalDate releaseDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public boolean isAccessory() {
		return accessory;
	}
	public void setAccessory(boolean accessory) {
		this.accessory = accessory;
	}
	public Game getExpansionFor() {
		return expansionFor;
	}
	public void setExpansionFor(Game expansionFor) {
		this.expansionFor = expansionFor;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}	
	
}
