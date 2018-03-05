package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StayLogTest {
	
	StayLog sl = new StayLog(DefinedAreas.glos, 1);
	StayLog sl1 = new StayLog(DefinedAreas.sitTrening, 2);
	
	@Test
	public void testsetStayTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(sl.getStayTime() == 0);
	}
	

}
