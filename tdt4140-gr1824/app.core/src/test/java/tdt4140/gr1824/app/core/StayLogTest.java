package tdt4140.gr1824.app.core;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class StayLogTest {
		
	@Test
	public void testStartTime() {
		StayLog sl = new StayLog(DefinedAreas.sitTrening, 2);
		long testTime = System.nanoTime();
		
		Assert.assertEquals(testTime, sl.getStartTime(), 1);
		
	}	
	
	@Test
	public void testStayTime() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		sl.stopStayLog();
		Assert.assertEquals(sl.getStayTime(), (NANOSECONDS.toSeconds(System.nanoTime() - sl.getStartTime()))/60);
	}
	
	@Test
	public void testStartDateString() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);
		Date startTestDate = new Date();
		
		Assert.assertEquals(String.format("%1$td-%1$tm-%1$tY", startTestDate), sl.getStartDateString());
		
	}
	
	@Test
	public void testStopDateString() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);		
		sl.stopStayLog();
		Date stopTestDate = new Date();
		
		Assert.assertEquals(String.format("%1$td-%1$tm-%1$tY", stopTestDate), sl.getStopDateString());
	}
	
	@Test
	public void testStartTimeString() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);		
		Date startTestDate = new Date();
		
		Assert.assertEquals(String.format("%1$tT", startTestDate), sl.getStartTimeString());
	}
	
	@Test
	public void testStopTimeString() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);		
		sl.stopStayLog();
		Date stopTestDate = new Date();
		
		Assert.assertEquals(String.format("%1$tT", stopTestDate), sl.getStopTimeString());
	}
	
	
	@Test
	public void testFileWriting() {
		StayLog sl = new StayLog(DefinedAreas.glos, 1);
		sl.stopStayLog();
		
		String lastLine = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("database.txt"));
			
			while (br.ready()) {
				lastLine = br.readLine();
			}
			
			br.close();
			
		} catch (IOException e) {
			System.err.println("Could not open file.");
		}
		
		assertTrue(lastLine.equals("1, glos, 0"));
		
	}
	
	

}
