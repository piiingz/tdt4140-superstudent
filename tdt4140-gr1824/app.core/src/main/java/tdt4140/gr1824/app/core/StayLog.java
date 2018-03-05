package tdt4140.gr1824.app.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

// Purpose: To log a stay. Interpreter calls StayLog when the person passes over to a new area. 
// StayLog registers the time this person stays in this area. 

public class StayLog {

	private Area area;
	private long inTime;
	private long stayTime;
	private int ID;
	
	public StayLog(Area area, int ID) {
		this.area = area;
		this.ID = ID;
		this.inTime = System.nanoTime();		
	}
	
	public Area getArea() {
		return this.area;
	}
	
	// Sets stayTime in minutes.
	private void setStayTime() {
		long stayTimeNS = System.nanoTime() - this.inTime;
		stayTime = (NANOSECONDS.toSeconds(stayTimeNS))/60;
		
	}
	
	// Used by interpreter to stop StayLog. Writing to database, format: ID, Area.name, stayTime.
	public void stopStayLog() {
		setStayTime();
		
		try {
			PrintWriter output = new PrintWriter(new FileWriter("database.txt", true));
			output.println("" + this.ID + ", " + this.area.getName() + ", " + this.stayTime);
			output.close();

		} catch (IOException e) {
			System.err.println("File could not be opened.");
			System.exit(1);
			}	
	}
	
	
	public long getStayTime() {
		return stayTime;
	}	

	
}
