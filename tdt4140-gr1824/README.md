							
							Superstudent
							
What is it?
-----------

Superstudent is infrastructure that reads and classifies NMEA data. It 
handles input from multiple users and will gather data about where each is
and how long they stay there. This information is then used to offer statistics
describing a groups patterns.

Release Notes
-------------

The full list of changes can be found at https://gitlab.stud.iie.ntnu.no/tdt4140-2018/24/commits/master/tdt4140-gr1824.

Running Superstudent
--------------------

1) We use Maven for CI in order to test the project we suggest installing it. Find instructions here:
	https://maven.apache.org/download.html#Installation
	
2) Clone the repo to the desired directory.

3) Navigate to src/test/java under app.core and run Demo.java
	this will allow you to see how information is collected, classified, and stored.