# WEATHER API

[![Java Version](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)

Welcome to the Weather API Project!

### Getting Started
The application can be started:
- In the application.yml file, change the values of the api keys.
```
external-service:
  tomorrow:
    apikey: # create an apikey here https://app.tomorrow.io
```
```
external-service:
  accuweather:
    apikey: # create an apikey here https://developer.accuweather.com
```
- Using Maven, it is very important that we first make a `mvn clean install` after that `mvn spring-boot:run`.

### Prerequisites

- Java Development Kit (JDK) 17 or higher: [Download JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- Git: [Install Git](https://git-scm.com/downloads)

### Installation

1. Clone the repository:

```bash
git clone https://github.com/fariasrod/weather
```

### Useful links

1. http://localhost:8080 Default Port
2. http://localhost:8080/swagger-ui/index.html Swagger
3. http://localhost:8080/actuator/health Actuator
