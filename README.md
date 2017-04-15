# Vehicle Survey Data Analyser
This project is created to be used as the solution to Vehicle Survey Coding Challenge offered by Aconex. The application is mainly an analyser for the survey data supposedly recorded by vehicle counter owned by the city government mentioned in the problem statement.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

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

```
JDK 8
```
Java Development Kit 8 is used to setup the development environment. Java 8 has new features like Stream API and the java.time package which is used in the project. Detailed instructions to install and setup Java on your system is provided in the link below:-

[JDK 8 Installation](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)

```
Eclipse IDE (Optional but recommended)
```
Eclipse is chosen as the IDE because of it's extensive community support and plugins and also since it is most mature. I have used Eclipse version 4.6 (Neon) and the package used is Eclipse IDE for Java Developers. This package includes everything else other than Java 8 which would help us to quickly build, run and test the solution. It includes:

    Git integration for Eclipse
    Eclipse Java Development Tools
    Maven Integration for Eclipse  

[Eclipse installation](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/neon3)

```
Maven (Optional and not necessary if the above Eclipse package is installed)
```
Maven can be considered as complete project development tool not just a build tool. I use Maven in almost all of my projects because it allows a lot of automation to be done in configuration management of the project. Just in case, you don't have Maven, instructions here:

[Maven installation](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

```
Git (Optional and not necessary if the above Eclipse package is installed)
```
Git is a free and open source distributed version control system. 
[Git](https://git-scm.com/downloads)


## Build and Deploy

Clone the project from GitHub using the following link:-
[vehicle-survey-analyser
](https://github.com/FelixRovinVincent/vehicle-survey-analyser.git)

use the following Maven command in Command Line:
mvn clean install -e
This would compile and build runnable Jar file "vehicle-survey-analyser-0.0.1-SNAPSHOT.jar" which can be found in "target" folder.

Note:-
I have included an Eclipse launch configuration for convenience to quickly enable you to build the project. Right click on the project folder in Eclipse IDE and choose "Run As"-> "Maven Build". Choose "vehicle-survey-analyser - Production" configuration and it will compile, execute tests, do static analysis using SonarQube and also build runnable Jar.

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```


## Built With

* [Java SE 8u121](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Latest stable version of Java
* [Maven](https://maven.apache.org/) - Project configuration management 
* [Eclipse 4.6 (Neon)](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/neon3) - IDE used


## Authors

* **Felix Rovin Vincent** 


## Acknowledgments

* The people at Aconex who have provided me an opportunity to solve this coding challenge

