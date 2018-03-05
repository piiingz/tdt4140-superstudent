package tdt4140.gr1824.app.core;

public class Location {
	// Latitude = y-koordinater, longitude = x-koordinater
	private double latitude, longitude;
	
	public Location(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Double getLong() {
		return this.longitude;
	}
	
	public Double getLat() {
		return this.latitude;
	}
}
