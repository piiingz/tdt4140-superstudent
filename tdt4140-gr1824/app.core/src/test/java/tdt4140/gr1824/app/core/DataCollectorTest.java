package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

public class DataCollectorTest {

	private String acceptedInput = "1,$GPGGA,181908.00,3404.7041778,N,07044.3966270,W,4,13,1.00,495.144,M,29.200,M,0.10,0000*40";
	private String unacceptedInput = "2,$GPGGA,181908.00,3404.7041778,N,07044.3966270,W,1,13,1.00,495.144,M,29.200,M,0.10,0000*40";
	private String expectedResultAccept = "1,3404.7041778,07044.3966270";
	
	private Interpreter dummyInterpreter = new Interpreter();
	private DataCollector collector = new DataCollector("125", dummyInterpreter);
	
	
	@Test
	public void testAcceptedCollect() throws SQLException {
		this.collector.collect(acceptedInput);
		assertTrue(this.collector.getLastParsedResult().equals(this.expectedResultAccept));
	}
	
	@Test
	public void testDiscardedCollect() throws SQLException {
		this.collector.collect(this.unacceptedInput);
		assertTrue(this.collector.getLastParsedResult() == null);
	}
	
	
}
