# Vehicle Survey Data Analyser
This project is the solution to Vehicle Survey Coding Challenge offered by Aconex. The application is mainly an analyser for the survey data supposedly recorded by vehicle counter owned by the city government mentioned in the problem statement.

## Why I chose Vehicle Survey Coding Challenge

The project caught my attention right from the first read out of the three challenges. This must be because of the following reasons:-

1. I found the problem to be more complex than the other two (admit to be subjective), but saw the potential to apply object oriented concepts to simplify it. Also the new additions in Java 8 like the Stream API and the java.time package could also be used to demonstrate it's usefulness. And to be honest, I believe both of these would be advantageous to better demonstrate my skills in application design and coding.

2. I started my career in Java with hardware integration like interfacing NFC, SmartCard readers, Fingerprint devices, RFID tag readers and a lot of other hardware devices to software written mainly in Java. I felt this particular challenge to be interesting in that context and knew that I would really enjoy solving the same.

## Design and approach

All the analysis and design documents I have created to solve the challenge are available in the [_DOCS](https://github.com/FelixRovinVincent/vehicle-survey-analyser/tree/master/_DOCS) folder. The steps I followed to solve the chosen challenge are as follows:-

1. Start the project by creating a GitHub repo.
2. Setup configuration management using Maven.
3. Configure logging with java.util.logging.
4. Add Static Analysis capability to the project.

[Code Quality measures](http://162.212.130.161:9000/sonar/projects)

5. Textual Analysis of the Problem Statement and other documents if any prepared in requirements gathering. It helps to identify and fix requirements, Actors, User stories, and most likely much of the System test cases, some of the Classes and their behaviour etc.

[Textual Analysis](https://github.com/FelixRovinVincent/vehicle-survey-analyser/tree/master/_DOCS/Analysis/Textual%20Analysis)

![UseCase Diagram](https://github.com/FelixRovinVincent/vehicle-survey-analyser/blob/master/_DOCS/Analysis/UseCaseDiagram.svg "UseCase Diagram")

6. Create README to include the following deliverables:
	a. An explanation on why I selected the chosen problem.
	b. Clear and concise instruction on how to execute the solution to the challenge.
7. Requirements Diagram which helps to ensure that no requirements are missed during design.

![Requirements Diagram](https://github.com/FelixRovinVincent/vehicle-survey-analyser/blob/master/_DOCS/Analysis/RequirementDiagram.svg "Requirements Diagram")

8. Identify and write System Test cases. The examples mentioned in the problem statement such as the explanation on how the data will be interpreted etc. are definitely System Test Case candidates.
9. Code design using UML Class Diagram.

![Class Diagram](https://github.com/FelixRovinVincent/vehicle-survey-analyser/blob/master/_DOCS/Design/Code%20Design/ClassDiagram.svg "Class Diagram")

10. Write Unit Tests especially against crucial methods.
11. Write Integration tests including negative scenarios.
12. Write test-driven production code as implementation of the design. Running tests to confirm implementation logic, static analysis of the code followed by corrections/improvements are repeated in sequence after implementing a portion of the solution. This would be the last deliverable.
13. Refactor code. There will always be room for improvement. But, hopefully the refactoring will be minimum as otherwise it would be a sign of poor application design.
14. Ensure everything is OK. Submit the solution via email including the link to GitHub repo.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for building and testing purposes.

### Prerequisites

What things you need to install. Please see below instructions on how to install if you do not have them.

```
JDK 8
```
```
Eclipse IDE (Optional but recommended)
```
```
Maven (Optional)
```
```
Git (Optional)
```
### Installing


#### JDK 8

Java Development Kit 8 is used to setup the development environment. Java 8 has new features like Stream API and the java.time package which is used in the project. Detailed instructions to install and setup Java on your system is provided in the link below:-

[JDK 8 Installation](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)


#### Eclipse IDE (Optional but recommended)

Eclipse is chosen as the IDE because of it's extensive community support and plugins and also since it is most mature. I have used Eclipse version 4.6 (Neon) and the package used is Eclipse IDE for Java Developers. This package includes everything else other than Java 8 which would help us to quickly build, run and test the solution. It includes:

    Git integration for Eclipse
    Eclipse Java Development Tools
    Maven Integration for Eclipse  

[Eclipse installation](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/neon3)


#### Maven (Optional and not necessary if the above Eclipse package is installed)

Maven can be considered as complete project development tool not just a build tool. I use Maven in almost all of my projects because it allows a lot of automation to be done in configuration management of the project. Just in case, you don't have Maven, instructions here:

[Maven installation](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)


#### Git (Optional and not necessary if the above Eclipse package is installed)

Git is a free and open source distributed version control system.

[Git](https://git-scm.com/downloads)


## Build and Deploy

### Clone from GitHub
Clone the project from GitHub using the following link:-
[vehicle-survey-analyser
](https://github.com/FelixRovinVincent/vehicle-survey-analyser.git)

### Build using Maven
Use the following Maven command in Command Line:
```
mvn clean install -e
```
This would compile, run tests and build runnable Jar file "vehicle-survey-analyser-0.0.1-SNAPSHOT.jar" which can be found in "target" folder.

The tests results can be found in "target/surefire-reports" folder.

### Run executable Jar file
Navigate to the "target" folder in project from Terminal(Command Line). Please make sure that you place the data file to be analysed in a folder named "data" and place the folder in the same directory as that the excecutable Jar file is in.

use the following command:
```
java -jar vehicle-survey-analyser-0.0.1-SNAPSHOT.jar
```
### View reports
After running the jar file, there will be a "logs" folder created containing the log files and also the "report" folder containing all generated report files. For best viewing experience, use WordPad with WordWrap set to "No Wrap". The reports can be viewed by any text editors since they are simple txt files.

### Note:-
I have included an Eclipse launch configuration for convenience to quickly enable you to build the project. Import the project as Eclipse project. Right click on the project folder in Eclipse IDE and choose "Run As"-> "Maven Build". Choose "vehicle-survey-analyser - Production" configuration and it will compile, execute tests, build runnable Jar and additionally do static analysis using SonarQube. But I am not sure if it would work right away in all platforms. I have checked with Windows 10 OS and Eclipse Neon and it works. 
## Code Quality and Tests

I have used Sonar Qube to ensure Code Quality and TestNG for automated tests.

### SonarQube

SonarQube is a leading platform for continuous The leading platform for Continuous Code Quality and covers the 7 sections of code quality

    Architecture and Design
    Unit tests
    Duplicated code
    Potential bugs
    Complex code
    Coding standards
    Comments
    
[SonarQube](https://www.sonarqube.org/)

The SonarQube Code Quality report for the project can be accessed from the following link:-
[Static analysis of the project](http://162.212.130.161:9000/sonar/dashboard?id=com.citygovernment.vehiclesurvey%3Avehicle-survey-analyser)

Note:-
I have included an Eclipse launch configuration for convenience to quickly enable you to run static analysis on the project. Right click on the project folder in Eclipse IDE and choose "Run As"-> "Maven Build". Choose "vehicle-survey-analyser - Production" configuration and it will compile, execute tests, build runnable Jar and additionally do static analysis using SonarQube. But I am not sure if it would work right away in all platforms. I have checked with Windows 10 OS and Eclipse Neon and it works. The SonarQube configured for the project is hosted on a private server owned by me and is only temporary.


### TestNG

TestNG is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful and easier to use.

[TestNG](http://testng.org/doc/)


## Built With

* [Java SE 8u121](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Latest stable version of Java
* [Maven](https://maven.apache.org/) - Project configuration management 
* [Eclipse 4.6 (Neon)](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/neon3) - IDE used


## Authors

* **Felix Rovin Vincent**  


## Acknowledgments

* The people at Aconex who have given me an opportunity to solve this coding challenge.

