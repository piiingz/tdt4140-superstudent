package tdt4140.gr1824.app.core;
import static org.junit.Assert.assertTrue;
import java.lang.Math;
import org.junit.Test;

import tdt4140.gr1824.app.mock.DummyClassDatabaseCommunicatorForStatisticsTest;

public class StatisticsTest {
	
	private DummyClassDatabaseCommunicatorForStatisticsTest dbcom = new DummyClassDatabaseCommunicatorForStatisticsTest();
	private Statistics statistics = new Statistics(dbcom);
	private double[] expectedUserArray = {0.2538, 0.203, 0.1878, 0.3553};
	private double[] expectedAllUserArray = {0.3046, 0.1523, 0.1878, 0.3553};
	private double[] expectedGroupArray = {0.1132, 0.3019, 0.3962, 0.1887};
	

	@Test
	public void testGetAllUserStatPercentage() {
		boolean flag = true;
		double[] resultArray = this.statistics.getAllUserStatPercentagetest();
		
		for (int i = 0; i < resultArray.length; i++) {
			if (Math.floor(resultArray[i] * 100) != Math.floor(this.expectedAllUserArray[i] * 100)) {
				flag = false;
			}
		}
		assertTrue(flag);
	}

	@Test
	public void testGetGroupStatPercentage() {
		boolean flag = true; 
		double[] resultArray = this.statistics.getGroupStatPercentagetest("Test");

		for (int i = 0; i < resultArray.length; i++) {
			if (Math.floor(resultArray[i] * 100) != Math.floor(this.expectedGroupArray[i] * 100)) {
				flag = false;
			}
		}
		assertTrue(flag);
	}
	
	@Test
	public void testGetUserStatistics() {
		boolean flag = true;
		double[] statResult = this.statistics.getUserStatPercentagetest(1);
		
		for (int i = 0; i < statResult.length; i++) {
			if (Math.floor(statResult[i] * 100) != Math.floor(this.expectedUserArray[i] * 100)) {
				flag = false;
			}
		}
		assertTrue(flag);
	}
}
