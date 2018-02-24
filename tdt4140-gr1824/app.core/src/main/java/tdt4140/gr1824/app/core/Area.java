package tdt4140.gr1824.app.core;

import tdt4140.gr1824.app.core.Location;

public class Area {
	//Alle Area-felt er kvadratiske. Fire point-objekt:
	private final Location northWest;
	private final Location northEast;
	private final Location southWest;
	private final Location southEast;
	
	public Area(Location nw, Location ne, Location sw, Location se ) {
		this.northWest = nw;
		this.northEast = ne;
		this.southWest = sw;
		this.southEast = se;
	}
	
	public boolean inArea(Location location) {
		//Forenklet metode; Antar at AREA er en firkant som ligger langs long/lat-linjer, kan ikke roteres.
		if ((location.getLat() > this.southEast.getLat()) && (location.getLat() < this.northEast.getLat())) {
			if (location.getLong() > this.northWest.getLong() && location.getLong() < this.northEast.getLong()) {
				return true;
			}
		}
		return false;
	}

	public Location getNorthWest() {
		return northWest;
	}

	public Location getNorthEast() {
		return northEast;
	}

	public Location getSouthWest() {
		return southWest;
	}

	public Location getSouthEast() {
		return southEast;
	}
}


