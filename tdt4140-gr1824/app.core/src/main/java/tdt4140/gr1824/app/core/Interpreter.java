package tdt4140.gr1824.app.core;

import java.util.Date;
import java.util.HashMap;

public class Interpreter {
	
	private DatabaseCommunicator dbcom;
	private StayLog stayLog;
	
	public Interpreter(DatabaseCommunicator dbcom) {
		this.dbcom = dbcom;
	}
	
	public Date getCurrentTime() {
		Date currentTime = new Date();
		return currentTime;
	}
	
	public void receive(String parsedResult) {
		String[] data = parsedResult.split(",");
		int currentUserID = Integer.parseInt(data[0]);
		
		String[] areaAndTime = dbcom.getUser(this, currentUserID); //TRENGER METODE FRA DBCOMM SOM GIR TILBAKE USER-OBJEKT
		String currentAreaName = areaAndTime[0];
		String currentStartTime = areaAndTime[1];
		
		Location location = buildLocation(data[1],data[2]);
		if(inDefinedArea(location).getName().equals(currentAreaName)) {
			return;
		}
		else {
			Date currentTime = getCurrentTime();
			this.stayLog.logStay(currentStartTime, currentTime, currentAreaName, currentUserID);
			dbcom.updateCurrentStay(currentUserID, inDefinedArea(location), currentTime);
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
}
