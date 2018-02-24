package tdt4140.gr1824.app.core;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
	Location location = new Location(63.4305149,10.3950528);
	
	@Test
	public void testGetLat() {
		Assert.assertEquals(63.4305149, this.location.getLat(), 0.0000005);
	}
	
	 @Test
	 public void testGetLong() {
		 Assert.assertEquals(10.3950528, this.location.getLong(), 0.0000005);
	 }
}
