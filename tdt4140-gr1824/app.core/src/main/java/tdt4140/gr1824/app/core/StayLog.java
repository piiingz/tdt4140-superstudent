package tdt4140.gr1824.app.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

// Purpose: To log a stay. Interpreter calls StayLog when the person passes over to a new area. 
// StayLog registers the time this person stays in this area. 

public class StayLog {

	private Area area;
	private long start; // used to measure elapsed time (stayTime)
	private long stayTime;
	private int ID;
	private Date startDate; // the date when the instance of stayLog is initiated
	private Date stopDate; // the date when it is stopped
	
	public StayLog(Area area, int ID) {
		this.area = area;
		this.ID = ID;
		this.start = System.nanoTime();
		this.startDate = new Date();
	}
	
	public Area getArea() {
		return this.area;
	}
	
	// Sets stayTime in minutes.
	private void setStayTime() {
		long stayTimeNS = System.nanoTime() - this.start;
		stayTime = (NANOSECONDS.toSeconds(stayTimeNS))/60;
	}
	
	// Used by interpreter to stop StayLog. Writing to database, format: ID, Area.name, stayTime.
	public void stopStayLog() {
		setStayTime();
		this.stopDate = new Date();
		
		try {
			PrintWriter output = new PrintWriter(new FileWriter("database.txt", true));
			output.println("" + this.ID + ", " + this.area.getName() + ", " + this.stayTime);
			output.close();

		} catch (IOException e) {
			System.err.println("File could not be opened.");
			System.exit(1);
			}	
	}
	
	public long getStartTime() {
		return this.start;
	}
	
	public long getStayTime() {
		return stayTime;
	}

	/* Don't know if it is necessary to be able to return the Date objects?
	 * 
	public Date getStartDateObj() {
		return this.startDate;
	}
	
	public Date getStopDateObj() {
		return this.stopDate;
	}
	*/
	
	// Returns the date on the format: dd-mm-YYYY
	public String getStartDateString() {
		return String.format("%1$td-%1$tm-%1$tY", startDate);	
	}
	
	public String getStopDateString() {
		return String.format("%1$td-%1$tm-%1$tY", stopDate);	
	}
	
	// Returns the initial time of the stay on the format: HH:MM:SS
	public String getStartTimeString() {
		return String.format("%1$tT", startDate);
	}
	
	public String getStopTimeString() {
		return String.format("%1$tT", stopDate);
	}
	
}
