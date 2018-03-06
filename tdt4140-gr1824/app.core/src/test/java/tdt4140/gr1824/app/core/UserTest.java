package tdt4140.gr1824.app.core;

import org.junit.Test;
import org.junit.Assert;

public class UserTest {
	
	@Test
	public void basicTest() {
		User user = new User(23);
		Assert.assertEquals(23, user.getId());
		user.setStayLog(DefinedAreas.samfundet);
		Assert.assertEquals(DefinedAreas.samfundet, user.getArea());
		
	}

}
