package com.gamecouch.gcs.inventory;

import com.gamecouch.gcs.gamecouchsystem.Location;

import javax.persistence.*;

@Entity
@IdClass(GameLocationPK.class)
public class Game_Location {
	@Id
	@ManyToOne
	private Game game;
	@Id
	@ManyToOne
	private Location location;
	private int quantity;
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}