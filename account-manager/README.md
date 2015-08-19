Account Manager for JDBC(MySQL)
===================


How to Start
-------------------
####Step 1.
>  import cork.sql to MySQL 

####Step 2.
> config main/resources/**application.properties**
> >spring.datasource.url=jdbc:mysql://localhost:3306/cork (database name)
> spring.datasource.username=root 
> spring.datasource.password=password
> spring.datasource.driverClassName=com.mysql.jdbc.Driver
> spring.jpa.hibernate.dialect= org.hibernate.dialect.MySQLInnoDBDialect

####Step 3.
> **[$~/ProjectPath/]** gradle clean build

----------


Note
-------------
A template for Spring Boot Using  JDBC(MySQL).