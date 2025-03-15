# Project Setup and Running Locally

<h3>API Endpoints</h3>

- POST /crawl
- GET /crawl/{id} 

<h3>Implemented</h3>

- Use Jsoup fetch web page links
- Implement multithreading
- Implement DTOs
- Implement a global exception handler
- Swagger API documentation
- One unit test case

<h3>Prerequisites</h3>

1) dependency 
- Spring Web 
- Spring Jpa
- Spring H2 database
- Jsoup
- Lombok
- mockito

<h3>Springboot version</h3>

- 3.4.3

<h3>Java version</h3>
- 17


<h3>Configure the Database</h3>

```properties
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```


<h3>Build and Run the Project</h3>
- Open and Run project on Intellij.

<h3>localhost start on:</h3>
- Server running on Port 8082.
- If you want to change port then Change it in application.properties.

<h3>Swagger Screenshot:</h3>

![Screenshot 2025-03-15 153904](https://github.com/user-attachments/assets/0d9818f8-34cb-4795-a388-c34c7b77dc1e)

