# tickets-ms
management of tickets for double v patners 

## How to Run

This application is packaged as a jar which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository https://github.com/juanLuisAguilarGarcia/users-ms
* Make sure you are using JDK 1.17, Maven 3.6.3 and spring boot 3.3.4
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/users-ms-1.0-SNAPSHOT.jar
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=default"
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```
## How to Create database objects

run sql follow sql script:

```
CREATE TABLE dvp.tickets (
    ticket_id  bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ticket_user_id bigint NOT NULL,
    description varchar(500) NOT NULL, 
    status enum('ABIERTO','CERRADO') NOT NULL,   
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    FOREIGN KEY(ticket_user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE 
); 
```

## About the Service

Gcloud url: http://double-v-patners.ue.r.appspot.com

Here are some endpoints you can call:

### Get information about system health, configurations, etc.

``` 
http://localhost:8081/health
http://localhost:8081/info
http://localhost:8081/metrics
```

### Create a ticket resource

```
POST /tickets/api/v1/ticket
Accept: application/json
Content-Type: application/json

{ 
    "user_id": 1,
    "description" : "ticket para instalacion de windows 2" ,
    "status" : "ABIERTO"
}

RESPONSE: HTTP 201 (Created) 
```

### Get ticket by id

```
GET /tickets/api/v1/ticket/1
Accept: application/json
Content-Type: application/json

Response: HTTP 200 
```

### Get all pagination tickets  

```
GET /tickets/api/v1/ticket?page=1&size=1
Accept: application/json
Content-Type: application/json

Response: HTTP 200 
```

### Update a ticket resource

```
PUT /tickets/api/v1/ticket/1
Accept: application/json
Content-Type: application/json

{ 
    "user_id": 1,
    "description" : "ticket para instalacion de windows 2" ,
    "status" : "ABIERTO"
}

RESPONSE: HTTP 200 
``` 

### Delete a ticket resource

```
DELETE /tickets/api/v1/ticket/1
Accept: application/json
Content-Type: application/json 

RESPONSE: HTTP 200 
``` 

### Delete a ticket resource

```
DELETE /tickets/api/v1/ticket/1
Accept: application/json
Content-Type: application/json 

RESPONSE: HTTP 200 
``` 

### Get by filter tickets

```
GET /tickets/api/v1/ticket?client_id=1&status=ABIERTO
Accept: application/json
Content-Type: application/json 

RESPONSE: HTTP 200 
``` 

### To view Swagger 2 API docs

Run the server and browse to http://double-v-patners.ue.r.appspot.com/ticktes/api/v1/swagger-ui/index.html

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

**/metrics** Shows “metrics” information for the current application.

**/health** Shows application health information.

**/info** Displays arbitrary application info.

**/configprops** Displays a collated list of all @ConfigurationProperties.

**/mappings** Displays a collated list of all @RequestMapping paths.

**/beans** Displays a complete list of all the Spring Beans in your application.

**/env** Exposes properties from Spring’s ConfigurableEnvironment.

**/trace** Displays trace information (by default the last few HTTP requests).

# Questions and Comments: juanaguilargarcia20@gmail.com 