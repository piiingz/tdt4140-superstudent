package tdt4140.gr1824.app.core;

import org.junit.Test;
import org.junit.Assert;

public class NMEAdataTest {
	
	private NMEAdata data = new NMEAdata();
	
	@Test
	public void getSetTest() {
		data.setId(4);
		Assert.assertTrue(data.getId() == 4);
		data.setQuality(1);
		Assert.assertTrue(data.getQuality() == 1);
		data.setDegLatitude("050");
		Assert.assertTrue(data.getDegLatitude().equals("050"));
		data.setDegLongitude("9809808");
		Assert.assertTrue(data.getDegLongitude().equals("9809808"));
	}

}
