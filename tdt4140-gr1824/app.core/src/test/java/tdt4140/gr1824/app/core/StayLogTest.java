package tdt4140.gr1824.app.core;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
