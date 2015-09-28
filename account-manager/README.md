Account Manager for Spring Basic, MongoDB(Mix)
=============


How to Start
-------------------
####Step 1.

> **[$~/ProjectPath/]** gradle clean build

> **[$~/ProjectPath/]** java -jar build/libs/project.jar

Usage
-------------------
####annotation

The annotation @UseBasicDB will load about org.yugzan.account.basic configuration.
<code>
**@UseBasicDB**
</code>

The @UseMongoDB annotation will load about org.yugzan.account.mongo configuration.
<code>
**@UseMongoDB**
</code>

The @AccountManagerApplication merge @ComponentScan and @EnableAutoConfiguration support basePackages.
* resourceUri	: the resource's uri. 
* staticContent	: the mapping path.
<code>
@AccountManagerApplication(
		basePackages = { "org.test.account", UseDBClassPath.MONGO}, 
		resourceUri = 	{"/myuri/**" }, 
		staticContent = { "classpath:/web/" })
</code>

Note
-------------
A template for Spring Boot Using  Spring Basic, MongoDB(Mix)
