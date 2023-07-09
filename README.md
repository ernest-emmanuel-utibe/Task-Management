#### TASK_MANAGEMENT_SYSTEM

### INTRODUCTION:

Task management system is a web application developed for team project works where users can manage various task within team easily.
It is developed using Spring Boot(backend) framework.

The project is focused mainly on handling document based projects.
This web application is very secure and simple. I have implemented JWT Authentication and Authorization technique in this application.
The password is encrypted using public and private keys then is stored in database. I am using MySQL and Spring Data JPA for database management.


## Dependencies
    Spring Web
    Spring Data JDBC
    MySQL Database
    Spring Security
    Spring Validation
    Lombok
    Spring starter Web
    Java 17 JDK
    Embedded Tomcat 9 server
    Maven Installation
    Swagger-UI

## Backend

In IntelliJ IDE or similar IDE import the "backend" from this repo with option "import existing maven project.

Build the maven project to install all the required dependencies.

To set up database, install MySQL. Make any database.


Then update below the configuration fields in file
# application.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/task_management_api_db?createDatabaseIfNotExist=true
    spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update
    server.port=8080
    spring.datasource.username={use your database username}
    spring.datasource.password={use your database password}


Run the project from TaskManagementApplication.java, all the tables will be initialised in database with its first run.