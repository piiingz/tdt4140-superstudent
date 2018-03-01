package tdt4140.gr1824.app.core;

public class Location {
	// Latitude = y-koordinater, longitude = x-koordinater
	private double latitude, longitude;
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLong() {
		return this.longitude;
	}
	
	public double getLat() {
		return this.latitude;
	}
}
