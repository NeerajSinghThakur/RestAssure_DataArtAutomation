## User Cases Service

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

What tools do you need to get the application running?

* JDK 17

## How to use

### 1. Run the project
#### 1a. Use jar file
```
 java -jar user-cases-service-0.0.1-SNAPSHOT.jar
```

### 2. REST API Documentation
You can access and explore REST API when application is running.

Documentation is available (only after completing step 1) under:
```
http://localhost:8080/docs
```

### 3. Optionally you can access H2 console to explore in-memory database

| Name | Value |
  |:----------|:---------------------|
| H2 Conosle URL | http://localhost:8080/h2-console | 
| JDBC URL | jdbc:h2:mem:casesdb |
| Username | sa |
| Password | password |

### 4. Framework is developer with RestAssure and TestNG
Test Case in under src/test/java/com/testPages

### 5. Run Automation suit
Two way can run automation suits 
1. Directing with class and run with testNG
2. Run with TestNG.XML file 
3. Reporting part is using Extent Report like below 
<img width="1437" alt="image" src="https://user-images.githubusercontent.com/49934702/168432480-7e46fe34-8efe-4ddd-91fb-5d85bdf3bcb9.png">

