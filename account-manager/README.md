Account Manager for Spring Data MongoDB
=============


How to Start
-------------------
####Step 1.

> **[$~/ProjectPath/]** gradle clean build

> **[$~/ProjectPath/]** java -jar build/libs/project.jar

Usage
-------------------
####annotation
must to load class path "org.yugzan.account"  on main.
<code>
**@ComponentScan("org.yugzan.account")**
</code>

<code>
@EnableAccountManager(resourceUri = {"/org/\*\*"} ,staticContent = {"classpath:/web/" }  )
</code>

Note
-------------
A template for Spring Boot Using  Spring Data MongoDB
