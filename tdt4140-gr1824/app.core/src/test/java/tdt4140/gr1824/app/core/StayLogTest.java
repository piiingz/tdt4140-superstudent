package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;
import java.util.Date;

import org.junit.Test;

public class StayLogTest {

	public StayLog stayLog = new StayLog(); 
	
	@Test
	public void testStringToDate() {
		String stringDate = "2018-03-21 15:37:45";
		Date date = this.stayLog.stringToDate(stringDate);
		assertTrue(date.toString().substring(0, 18).equals(("Wed Mar 21 15:37:45 CET 2018").substring(0, 18)));
	}
	
	@Test
	public void testCalculateDuration() {
		String startTime = "2018-03-21 15:37:45";
		String stopTime = "2018-03-21 17:37:45";
		Date startTimeDate = this.stayLog.stringToDate(startTime); 
		Date stopTimeDate = this.stayLog.stringToDate(stopTime);
		assertTrue(this.stayLog.calculateDuration(startTimeDate, stopTimeDate) == 120);
	}
}
