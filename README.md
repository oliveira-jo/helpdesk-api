# HELPDESK PROJECT

> Status: Developing 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)


## Objective
The objective of this project is the development of an API to provide a system of HelpDesk


## Project Tamplate MVC
+ CONTROLLERS (DTO's)
+ SERVICES (DTO's)
+ REPOSITORIES (Entities)
+ MODELS (Entities)


## Technologies Used
* Java
* Spring Boot Starter Web: Servidor Tomcat
* Spring Boot Devtools: Hot Heload 
* Spring Boot Starter Data JPA: Persistence
* MySQL Data Base: BD
* MySQL Driver
* Lombok: Genereted methos Get, Set, and others
* Flyway: Data Base Versions
* Mapper MapStruct: Mapping the objects, dts and domain  
* Java Docks / Swagger: API Documentation
* Spring Boot Starter Security 


## Features Under Development
+ ✅ Create Controller 
+ ✅ Create Repositories
+ ✅ Create Services
+ ✅ Create Entities
+ ✅ Create Data Base and Drive Conection
+ ✅ Mapping objects: entities / dtos / domain
+ ✅ Create Busines Ruless in Services
+ ✅ Create a Handle Exceptions Controller 
+ ✅ Create a API Documentation Swagger
+ ✅ Configure the spring security 
+ ✅ Create a JWT Token and password crypt in BD
+ ✅ Create User ROLES
+ ✅ Get all tickets by role, Admin and attendant see all and customer see their tickets
+ ✅ Get tickets by id with role, Admin and attendant AND customer 


## Configuring the Database
### In the file Application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk
spring.datasource.username=YOUR-USER
spring.datasource.password=YOUR-PASSWORD
spring.jpa.hibernate.ddl-auto=update 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Flyway for table versions in DB
### The version contain sql is in

+ ✅ resourses/db/migration/versions.sql

## Configurations Spring Security and COR's
### The configurations is in

+ ✅ src/main/java/com/oliveira/helpdesk/configuration/SecurityConfig.java

## Configuration Admin and Attendent in BD
### Create and configurate expecials users

+ ✅ Every time when the system run, Create if not exist this users in BD
+ ✅ src/main/java/com/oliveira/helpdesk/configuration/UserConfig.java


## API Endpoints
### Using Swagger ui for map the api andpoins 

+ ✅ The endpoints can be accessed in browser by 
```
http://localhost:8000/api/v1/swagger-ui/index.html#/
```









