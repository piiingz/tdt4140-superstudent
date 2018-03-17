package tdt4140.gr1824.app.core;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Demo {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner inputFile = new Scanner(Demo.class.getResourceAsStream("demoInput"));
		Scanner console = new Scanner(System.in);
		Interpreter interpreter = new Interpreter();
		DataCollector dataCollector = new DataCollector("1235", interpreter);
		while (inputFile.hasNext()) {
			console.next();
			dataCollector.collect(inputFile.nextLine());
			System.out.println(interpreter.getUser(1));
			System.out.println(interpreter.getUser(2));
			System.out.println(interpreter.getUser(3));
		}
		interpreter.getUser(1).stopStayLog();
		interpreter.getUser(2).stopStayLog();
		interpreter.getUser(3).stopStayLog();
		inputFile.close();
		console.close();
	}

}
