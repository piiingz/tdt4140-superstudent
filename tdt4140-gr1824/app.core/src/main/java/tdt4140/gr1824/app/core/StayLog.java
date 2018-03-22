package tdt4140.gr1824.app.core;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import tdt4140.gr1824.app.db.DatabaseCommunicator;


// Purpose: To log a stay. Interpreter calls StayLog when the person passes over to a new area. 
// StayLog registers the time this person stays in this area. 

public class StayLog {
	
	// Used by interpreter to log stays to database. Writing to database, format: ID, Area.name, stayTime.
	public void logStay(String currentStartTime, Date stopTime, String currentAreaName, int currentUserID) {
		Long duration = this.calculateDuration(this.stringToDate(currentStartTime), stopTime);
		
		try {
			DatabaseCommunicator.addStay(currentStartTime, duration, currentAreaName, currentUserID);
		} catch (Exception e) {
			System.out.println("Trouble while trying to add stay to database.");
		}
	}
	
	public Date stringToDate(String timeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateFormat = LocalDateTime.parse(timeString, formatter);
		Date dateFormat = Date.from(localDateFormat.atZone(ZoneId.systemDefault()).toInstant());
		return dateFormat;
	}
	
	public Long calculateDuration(Date startTime, Date stopTime) {
		Long duration = stopTime.getTime() - startTime.getTime();
		return duration / 1000 / 60;
	}
}
