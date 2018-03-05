package tdt4140.gr1824.app.core;
import tdt4140.gr1824.app.core.Location;
import java.awt.Polygon;


public class Area {
	// x-points = longitude, y-points = latitude, npoints = number of points
	private int[] ypoints;
	private int[] xpoints;
	private int npoints;
	private Polygon areaPolygon;
	
	// Locations needs to be in a clockwise order
	public Area(Location...locations) {
		this.npoints = locations.length;
		this.ypoints = new int[locations.length];
		this.xpoints = new int[locations.length];
		for (int i = 0; i < npoints; i++) {
			ypoints[i] = (intConverter(locations[i].getLat()));
			xpoints[i] = (intConverter(locations[i].getLong()));
		}
		this.areaPolygon = new Polygon (this.xpoints, this.ypoints, this.npoints);
	}
	
	// Maps a double to int with 6 digits
	private static Integer intConverter (double number) {
		Double doubleCoord = Double.valueOf(number*1000000);
		double tempDouble = doubleCoord.doubleValue();
		int tempCoord = (int) tempDouble;
		Integer intCoord = Integer.valueOf(tempCoord);
		return intCoord;
	}
	
	public String getYpoints(){
		String result = "";
		for (int ypoint: this.ypoints) {
			result += Integer.toString(ypoint) + ", ";
		}
		return result;
	}
	public String getXpoints(){
		String result = "";
		for (int xpoint: this.xpoints) {
			result += Integer.toString(xpoint) + ", ";
		}
		return result;
	}
	
	public boolean inArea(Location location) {
		return areaPolygon.contains(intConverter(location.getLong()), intConverter(location.getLat()));
	}
}
