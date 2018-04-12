package tdt4140.gr1824.app.core;

import java.sql.SQLException;
import java.util.Date;

import tdt4140.gr1824.app.db.DatabaseCommunicator;


public class Interpreter {
	
	private StayLog stayLog = new StayLog();
	
	// Gives current time as a Date object.
	public Date getCurrentTime() {
		Date currentTime = new Date();
		return currentTime;
	}
	
	//receives data on the format ("userID, latitude, longitude"). 
	//receives current area and time for current user from database.
	//if user changes location it calls stayLog to log last stay. It then calls DatabaseCommunicator to update new current area and starttime.
	public void receive(String parsedResult) throws SQLException {
		String[] data = parsedResult.split(",");
		int currentUserID = Integer.parseInt(data[0]);
		
		String[] areaAndTime = DatabaseCommunicator.getCurrentStay(currentUserID); 
		String currentAreaName = areaAndTime[0];
		String currentStartTime = areaAndTime[1];
		
		Location location = buildLocation(data[1],data[2]);
		if(inDefinedArea(location).getName().equals(currentAreaName)) {
			return;
		}
		else {
			String currentTime = dateToDatetimeString(getCurrentTime());
			
			
			this.stayLog.logStay(currentStartTime, currentTime, currentAreaName, currentUserID);
			DatabaseCommunicator.updateCurrentStay(currentUserID, inDefinedArea(location).getName(), currentTime);
			}
		}
	
	//Takes a location as an argument and returns which of the defined areas it is located inside.
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
		return DefinedAreas.other;
	}
	
	//Formats a date to a string for the database.
	public String dateToDatetimeString(Date date) {
		return ""+String.format("%1$tY-%1$tm-%1$td", date)+" "+String.format("%1$tT", date);
	}
}

