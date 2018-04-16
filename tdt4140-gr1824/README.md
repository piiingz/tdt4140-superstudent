# Superstudent							

This is a project built as part of the course TDT4140, spring 2018. 
Based on this [template project](https://gitlab.stud.iie.ntnu.no/tdt4140-staff/examples/blob/master/tdt4140-gr18nn/README.md), the purpose of the project is to "Plan and manage small software engineering projects and participate as designer/programmer/tester in larger software projects." See: [Course Information] (https://www.ntnu.edu/studies/courses/TDT4140#tab=omEmnet)

*Superstudent* is a system intended for SiT - Student welfare organization in Trondheim
The system reads and classifies NMEA data. 
It handles input from multiple users (Students) and will gather data about where each user is
and how long they stay at predefined areas. This information is then used to offer statistics
describing a group's patterns, so that SiT can improve their services. *Superstudent* also allow SiT to create competitions where the students can win prizes. 

## Getting started

### Prerequisites

We develop using **Eclipse**. Other IDEs should work, but are not explicitly supported.
To run the project we suggest you use **JDK 8**.

### Installing

* We use **Maven** to build and manage the project, we suggest installing it. Find instructions here:
	https://maven.apache.org/download.html#Installation 
	
* Clone the **Repository** to the desired directory.

* In **Eclipse** use **import... > Existing Maven projects**

* Navigate to **app.ui/src/main/java/tdt.4140gr1824.app.ui** and run *AppStart.java*
as java application. To view user statistics use userID 1-15 and password "password". 


## Release Notes

The full list of changes can be found at https://gitlab.stud.iie.ntnu.no/tdt4140-2018/24/commits/master/tdt4140-gr1824.

## Built with

* [Jetty](https://www.eclipse.org/jetty/) - embedded HTTP server
* [Jersey] (https://jersey.github.io/) - Restful web services
* [JFoenix] (http://www.jfoenix.com/) - JavaFX material design library
* [TestFX](https://github.com/TestFX/TestFX) - test framework for JavaFX apps
* [Jackson](https://github.com/FasterXML/jackson) - [JSON](https://www.json.org) library


## Authors

* Adam Gaidi
* Alexander Michael Staff
* Espen Sørhaug
* Ingvild Unander Netland
* Maria Hilmo Jensen
* Mathea Godø Hildre
* Robin Christian Staff