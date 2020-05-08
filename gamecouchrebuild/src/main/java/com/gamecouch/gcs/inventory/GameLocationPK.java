package com.gamecouch.gcs.inventory;



import java.io.Serializable;

import com.gamecouch.gcs.gamecouchsystem.Location;

public class GameLocationPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4291083841194542823L;
	private Game game;
	private Location location;
	
	public GameLocationPK() {
		super();
	}

	public GameLocationPK(Game game, Location location) {
		super();
		this.game = game;
		this.location = location;
	}



	public Game getGame() {
		return game;
	}


	public Location getLocation() {
		return location;
	}
	
	@Override
	public int hashCode() {
		return (int) game.hashCode() + location.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
        if (!(obj instanceof GameLocationPK)) return false;
        if (obj == null) return false;
        GameLocationPK pk = (GameLocationPK) obj;
        return pk.game == game && pk.location.equals(location);
	}
}
