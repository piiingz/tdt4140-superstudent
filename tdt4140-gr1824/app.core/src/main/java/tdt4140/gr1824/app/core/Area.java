package tdt4140.gr1824.app.core;

import tdt4140.gr1824.app.core.Location;
import java.util.List;
import java.util.ArrayList;


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
	
	/*
	// x-points = longitude, y-points = latitude
	private final List<Double> ypoints = new ArrayList<>();	
	private final List<Double> xpoints = new ArrayList<>();
	
	// Locations needs to be in a clockwise order
	// TODO: Throw exception when number of locations <= 2
	public Area(Location...locations) {
		for (Location location:locations) {
			ypoints.add(location.getLat());
			xpoints.add(location.getLong());
		}
	}
	
	// Raycast algorithm
	public boolean inArea(Location location) {
		int hits = 0;
		double x = location.getLong();
		double y = location.getLat();
		
		double lastx = xpoints.get(xpoints.size()-1);
		double lasty = ypoints.get(ypoints.size()-1);
		double curx, cury;
		
		for (int i=0; i < xpoints.size(); lastx = curx, lasty = cury, i++) {
			curx = xpoints.get(i);
			cury = ypoints.get(i);
			
			if (intersect(x, y, lastx, lasty, curx, cury)) {
				hits++;
			}
		}
		// Return true if hits = odd number
		return (hits%2 != 0);
	}
	
	// http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7-b147/java/awt/Polygon.java#Polygon.contains%28double%2Cdouble%29
	private boolean intersect (double x, double y, double lastx, double lasty, double curx, double cury) {
		return true;
	}
	*/
	



