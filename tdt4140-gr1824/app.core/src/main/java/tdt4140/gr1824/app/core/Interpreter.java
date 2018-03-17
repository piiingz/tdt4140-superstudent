package tdt4140.gr1824.app.core;

import java.util.HashMap;

public class Interpreter {
	
	private HashMap<Integer, User> users = new HashMap<Integer, User>();
	private int currentUserID;

	public void receive(String parsedResult) {
		String[] data = parsedResult.split(",");
		this.currentUserID = Integer.parseInt(data[0]);
		Location location = buildLocation(data[1],data[2]);
		if (!(users.containsKey(currentUserID))) {
			createUser(location);
		}
		else {
			if(inDefinedArea(location) == users.get(currentUserID).getArea()) {
				return;
			}
			else {
				users.get(currentUserID).stopStayLog();
				users.get(currentUserID).setStayLog(inDefinedArea(location));
			}
		}	
	}	
	
	//Quick maths for � gj�re NMEA coordinater som er p� formen minutter og grader om til desimalform. 
	//https://stackoverflow.com/questions/36254363/how-to-convert-latitude-and-longitude-of-nmea-format-data-to-decimal
	public Location buildLocation(String latitudeNMEA, String longitudeNMEA) {
		String latitudePart0 = latitudeNMEA.substring(0, 3);
		String latitudePart1 = latitudeNMEA.substring(3);
		String longitudePart0 = longitudeNMEA.substring(0, 3);
		String longitudePart1 = longitudeNMEA.substring(3);
		double longitude = Double.parseDouble(longitudePart0) + (Double.parseDouble(longitudePart1)/60);
		double latitude = Double.parseDouble(latitudePart0) + (Double.parseDouble(latitudePart1)/60);
		Location location = new Location(latitude,longitude);
		return location;
	}
	
	public Area inDefinedArea(Location location) {
		for (Area area: DefinedAreas.areas) {
			if(area.inArea(location)) {
				return area;
			}
		}
		return DefinedAreas.nowhere;
	}
	
	public void createUser(Location location) {
		users.put(currentUserID, new User(currentUserID));
		users.get(currentUserID).setStayLog(inDefinedArea(location));
	}
	
	public User getUser(int ID) {
		return users.get(ID);
	}
	

}
