Account Manager for HttpSecurity Basic
=============


How to Start
-------------------
####Step 1.

> **[$~/ProjectPath/]** gradle clean build

> **[$~/ProjectPath/]** java -jar build/libs/project.jar

Usage
-------------------
####annotation
<code>
@EnableAccountManager(user = "test" , pw = "test", role="USER", resourceUri = {"/org/\*\*"} ,staticContent = {"classpath:/web/" }  )
</code>

Note
-------------
A template for Spring Boot Using  HttpSecurity Basic
