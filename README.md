spring-study
============
In This Project
memos about the study sample code.


####account-manager
-------------------
Spring Boot integrate account manager.

[Basic](https://github.com/yugzan/spring-study/tree/develop/account-manager)

[JDBC(MySQL)](https://github.com/yugzan/spring-study/tree/jdbc-version/account-manager)

[Jongo(MongoDB)](https://github.com/yugzan/spring-study/tree/jongo-version/account-manager)




####sample gmail deliver
-------------------
This is a mail deliver using Google mail and Spring boot Framework.


Step 1.
To init it needs edit this path main/config/application.yml to setting a gmail account.

maildeliver:
  host: smtp.gmail.com 	#default gmail host
  port: 587 			#default gmail port
  username: your@gmail.com
  password: "yourpassword"

Step 2. 
This project is using Gradle 

run commands 
	
	gradle clean build

download depandeies and build it.

Step 3. 
run shellscript app.sh

./build/libs/app.sh

