# Banking Application API

This project aims to provide APIs for several banking 
operations.

### Getting Started 

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This project is built using Maven.
Make sure to have maven installed on your machine.

For deploying containerized image, you would need docker 
installed on your machine.

You would need Java 1.8 or above.

```
mvn clean install
```

### Installing

A step by step series of examples that tell you how to get a development env running

Create a folder on your local machine

```
mkdir banking-app
cd banking-app
```

Download the code in newly created folder by cloning this repo.

```
git clone https://github.com/mgonawala/blue-harvest.git
```

It can be build using either mvn or mvn wrapper or Docker

use one of the below commands for building using Maven.
```
mvn clean install
./mvnw clean install ( use this if you don't have maven on your machine)

```
On a successful completion of build you should see something like below
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:43 min
[INFO] Finished at: 2019-07-31T18:30:24+05:30
[INFO] Final Memory: 53M/508M
[INFO] ------------------------------------------------------------------------
```
This will generate an executable jar file which can be
found under target folder (account-0.0.1-SNAPSHOT.jar)

Jacoco code coverage report can be found under target folder 
```
target/site/jacoco/index.html
```

To evaluate project using Sonar use below command.
Change Sonar host url to your sonar sever.
```
sonar:sonar -Dsonar.host.url=http://localhost:9000
```
Docker image can be build by below command.
```
docker build -t blueharvest/banking-app:latest .
```

### Deployment

These steps let you deploy app on your local machine.
Once deployed head to http://localhost:8585/swagger-ui.html to get a view
of all the exposed APIs & it's structure.

Port can be changed from application.properties file.
Just change below property in application.properties file, rebuild & deploy.

```
server.port=8585
``` 

Use any of below commands to deploy the app & get going.

```
java -jar target/account-0.0.1-SNAPSHOT.jar
mvn spring-spring:run
docker run -d --name blueharvest/banking-app:latest -p 8585:8585
```

You should be able to access the app on http://localhost:8558/api/v1/

### Built With

Below is a list of Dependencies used in this project.

*  Spring Boot version - 2.1.6.RELEASE
*  Swagger 2 - 2.6.1
*  Maven check style plugin - 3.1.0
*  jacoco-maven-plugin - 0.8.4
*  hsqldb
*  Java version 1.8

### Working Demo

This app is deployed on Heroku cloud.
Live URL: https://blue-harvest-mohini.herokuapp.com/swagger-ui.html

Sonar token:08775b091bce5062c82c849c5d929d83f7639a51
