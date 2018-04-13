package tdt4140.gr1824.app.core;

public class NMEAdata {
	
	private int id;
	private int quality;
	private String degLatitude, degLongitude;
	
	public int getId() {
		return id;
	}
	public int getQuality() {
		return quality;
	}
	public String getDegLatitude() {
		return degLatitude;
	}
	public String getDegLongitude() {
		return degLongitude;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public void setDegLatitude(String degLatitude) {
		this.degLatitude = degLatitude;
	}
	public void setDegLongitude(String degLongitude) {
		this.degLongitude = degLongitude;
	}
	
	@Override
	public String toString() {
		return "NMEAdata id=" + id + ", quality=" + quality + ", degLatitude=" + degLatitude + ", degLongitude="
				+ degLongitude;
	}
	

}
