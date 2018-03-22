							
							Superstudent
							
What is it?
-----------

Superstudent is a system that reads and classifies NMEA data. It 
handles input from multiple users and will gather data about where each user is
and how long they stay at predefined areas. This information is then used to offer statistics
describing a group's patterns.

Release Notes
-------------

The full list of changes can be found at https://gitlab.stud.iie.ntnu.no/tdt4140-2018/24/commits/master/tdt4140-gr1824.

Running Superstudent
--------------------

1) We use Maven for CI in order to test the project we suggest installing it. Find instructions here:
	https://maven.apache.org/download.html#Installation, you also need to install jfoenix to build the UI. 
	
2) Clone the repo to the desired directory.

3) Navigate to app.ui/src/main/java/tdt.4140gr1824.app.ui and run AppStart.java
as a java application to view statistics. 
To enter gpsdata to the database: Pass a string on format: "userID,NMEA" to the method
'collect(String NMEAstring)' in app.core/DataCollector.java