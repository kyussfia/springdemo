# Spring Demo
A basic web page with login and permission handling written in Java with Spring Framework.
Spring Security does the job with the security layer, session functionality is provided by Spring Session, the controllers are supported by Spring Boot and the View is written with Thymeleaf.
## Requirements
This MVC application require a MySQL database for its persistence layer.
The default configured database is `springdemo`, that can be changed in the application.properties file.
(The query to create the database is in the schema.sql file under resources)
The main requirement for this project is a java jre installed on the target computer.
## Installation
No complex installation is needed, only a java is what u need to package the source into a `.jar` file and run it with:
<pre>java -jar demo-application.jar</pre>
A "external" application.properties file next to the jar file is override the whole application.properties compiled into the jar, so you can change it for your needs.
## First run
The default configuration is about to create the whole schema under the empty `springdemo` database, and fill it with startup data.
So there is no more action to be executed.
## Other runs
But if you want to run it more, you have to override a parameter to avoid your SQL database's constraint fails.
The property to modify is the `spring.datasource.initialization-mode`. It is `always` by default, but that means, it wants to create the whole schema and insert the test data on every startup.
The problem comes up with the data re-insertion, because there are unique key in your schema, which can't be overriden. So to acoid that after the First run, you have to change it to:
<pre>spring.datasource.initialization-mode = never</pre>
## Test Data
Username - password - granted roles:
* Admin - Admin - ADMIN
* User 1 - User 1 - EDITOR, USER
* User 2 - User 2 - EDITOR
* USer 3 - User 3 - USER
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

