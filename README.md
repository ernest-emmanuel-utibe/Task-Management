TASK_MANAGEMENT_SYSTEM

INTRODUCTION:

Task management system is a web application developed for team project works where users can manage various task within team easily.
It is developed using Spring Boot(backend) framework.

Project is focused mainly on handling document based projects.
This web application is very secure and simple. I have implemented OAuth2 based Authentication and Authorization technique in our application.
The password is encrypted using public and private keys then is stored in database. I am using MySQL and Spring Data JPA for database management.


Dependencies
Java 17 JDK
Embedded Tomcat 9 server
MySQL Database
Maven Installation
Backend
In IntelliJ IDE or similar IDE import the "backend" from this repo with option "import existing maven project".
Build the maven project to install all the required dependencies.
To set up database, install MySQL. Make any database.
Then update below three configuration fields in file application.properties inside /resources folder
spring.datasource.url=jdbc:mysql://localhost:8080/task_management_api_db
spring.datasource.username=root
spring.datasource.password=root
For email services you need to use email via SMTP. For that you need to update application.properties below fields.
spring.mail.host=smtp.gmail.com
spring.mail.smtp.ssl.trust=smtp.gmail.com
spring.mail.port=587
spring.mail.transport.protocol=smtp
spring.mail.username=your.email@gmail.com
spring.mail.password=password`

Run the project from TaskManagementApplication.java, all the tables will be initialised in database with its first run.
Now execute roles.sql on your database.