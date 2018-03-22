package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class StayLogTest {

	public StayLog stayLog = new StayLog(); 
	/*
	@Test
	public void testStringToDate() {
		String stringDate = "2018-03-21 15:37:45";
		Date date = this.stayLog.stringToDate(stringDate);
		assertTrue(date.toString().equals("Wed Mar 21 15:37:45 CET 2018"));
	}*/
	
	@Test
	public void testCalculateDuration() {
		String startTime = "2018-03-21 15:37:45";
		String stopTime = "2018-03-21 17:37:45";
		Date startTimeDate = this.stayLog.stringToDate(startTime); 
		Date stopTimeDate = this.stayLog.stringToDate(stopTime);
		assertTrue(this.stayLog.calculateDuration(startTimeDate, stopTimeDate) == 120);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartTimeAfterStopTime() {
		String startTime = "2018-03-21 18:37:45";
		String stopTime = "2018-03-21 17:37:45";
		Date startTimeDate = this.stayLog.stringToDate(startTime); 
		Date stopTimeDate = this.stayLog.stringToDate(stopTime);
		this.stayLog.calculateDuration(startTimeDate, stopTimeDate);
	}
}
