//TODO check NMEA format against lat long format
//TODO Write tests once Interpreter class is finished
package tdt4140.gr1824.app.core;

public class DataCollector {
	
	private String threshold; // String that contains unacceptable signal qualities 
	private Interpreter interpreter;
	
	public DataCollector(String threshold, Interpreter interpreter) {
		this.threshold = threshold;
		this.interpreter = interpreter;
	}
	
	private String parseNMEA(String[] data) {
		//Method extracts relevant information from the NMEA input-string, return null if data-quality is not acceptable
		if (threshold.contains(data[7])) {
			return null;
		}
		// Return "UserID,latitude,longitude"
		return data[0] + "," + data[3] + "," + data[5];
		
	}
	
	public void collect(String NMEAdata) {
		// Split due to NMEA format: http://gpsworld.com/what-exactly-is-gps-nmea-data/
		String[] data = NMEAdata.split(",");
		String parsedResult = parseNMEA(data);
		if (parsedResult != null) {
			interpreter.receive(parsedResult);
		}
	}

}
