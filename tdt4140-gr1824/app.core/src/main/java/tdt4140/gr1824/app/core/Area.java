package tdt4140.gr1824.app.core;
import tdt4140.gr1824.app.core.Location;
import java.awt.Polygon;


public class Area {
	// x-points = longitude, y-points = latitude
	private int[] ypoints;
	private int[] xpoints;
	private Polygon areaPolygon;
	private String name;
	
	// Locations needs to be in a clockwise order
	public Area(String name, Location...locations) {
		/* 
		 *Adds all coordinates from the inputs "locations" (Locations... = Variable number of arguments)
		 *extracts lat-coordinates to ypoints, long-coordinates to xpoints. Creates polygon-object to
		 *define area.
		 */
		this.name = name;
		int npoints = locations.length;
		this.ypoints = new int[locations.length];
		this.xpoints = new int[locations.length];
		for (int i = 0; i < npoints; i++) {
			ypoints[i] = (intConverter(locations[i].getLat()));
			xpoints[i] = (intConverter(locations[i].getLong()));
		}
		this.areaPolygon = new Polygon (this.xpoints, this.ypoints, npoints);
	}
	
	private static Integer intConverter (double number) {
		/*
		 *Takes a double number (coordinate) and maps it to a integer because the polygon object demands int coordinates.
		 *Scales the numbers up by 100 000 to keep accuracy, and returns scaled int
		 */
		Double doubleCoord = Double.valueOf(number*1000000);
		double tempDouble = doubleCoord.doubleValue();
		int tempCoord = (int) tempDouble;
		Integer intCoord = Integer.valueOf(tempCoord);
		return intCoord;
	}
	
	public boolean inArea(Location location) {
		/* Uses polygon built in method to check if a location is inside the area
		 * return false if not in area, true if in area. 
		 */
		return areaPolygon.contains(intConverter(location.getLong()), intConverter(location.getLat()));
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
