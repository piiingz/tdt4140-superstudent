package tdt4140.gr1824.app.core;

import java.util.HashMap;

public class Interpreter {
	
	private int currentUserID;
	private User currentUser;
	private DatabaseCommunicator dbcom;
	
	public Interpreter(DatabaseCommunicator dbcom) {
		this.dbcom = dbcom;
	}
	
	public void receive(String parsedResult) {
		String[] data = parsedResult.split(",");
		this.currentUserID = Integer.parseInt(data[0]);
		this.currentUser = dbcom.getUser(currentUserID); //TRENGER METODE FRA DBCOMM SOM GIR TILBAKE USER-OBJEKT
		Location location = buildLocation(data[1],data[2]);
		if(inDefinedArea(location) == currentUser.getArea()) {
			return;
		}
		else {
			currentUser.stopStayLog();
			currentUser.setStayLog(inDefinedArea(location));
			}
		}	

	
	//Quick maths for å gjøre NMEA coordinater som er på formen minutter og grader om til desimalform. 
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
}
