package tdt4140.gr1824.app.core;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

// Purpose: To log a stay. Interpreter calls StayLog when the person passes over to a new area. 
// StayLog registers the time this person stays in this area. 

public class StayLog {
	
	// Used by interpreter to stop StayLog. Writing to database, format: ID, Area.name, stayTime.
	public void logStay(String currentStartTime, Date currentTime, String currentAreaName, int currentUserID) {
	
		
		
		try {
			DatabaseCommunicator.addStay(this.dateTime, this.stayTime, this.area.getName(), this.userID);
		} catch (Exception e) {
			System.out.println("Trouble while trying to add stay to database.");
		}
	}
	

		// Returns the date on the format: dd-mm-YYYY
	public String getStartDateString() {
		return String.format("%1$td-%1$tm-%1$tY", startDate);	
	}
	
	// Returns the initial time of the stay on the format: HH:MM:SS
	public String getStartTimeString() {
		return String.format("%1$tT", startDate);
	}
	
	private Date stringToDate(String timeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
		
	}
}
