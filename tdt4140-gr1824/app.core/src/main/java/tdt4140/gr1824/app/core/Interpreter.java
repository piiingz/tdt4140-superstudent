//TODO write test for DataCollector
package tdt4140.gr1824.app.core;

public class Interpreter {
	
	private String parsedResult;
	
	public void receive(String parsedResult) {
		this.parsedResult = parsedResult;
	}

	public String getParsedResult() {
		return this.parsedResult;
	}
}
