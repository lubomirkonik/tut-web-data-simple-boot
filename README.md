Web application based on tutorials 
http://spring.io/guides/tutorials/data/, 
http://spring.io/guides/tutorials/web and 
http://www.thymeleaf.org/layouts.html with Spring Boot.

Registration and Login are implemented based on the Layouts project, forms with validation messages are created based on this project.
Administration section is added to the project, see screenshots.

The project doesn't follow principles of Hexagonal Architecture as original tutorial projects.

The app is running against a MongoDB document database, version 2.4.8 and an in-memory H2 relational database.

The version of Gradle: 1.12

### Steps

> _Instructions for Windows_

For MongoDB run the following :

```
[path]\bin\mongod.exe --dbpath [path]\data
```

``` 
[path]\bin\mongo.exe
```

> _see http://docs.mongodb.org/manual/_

From the root directory, run the app directly from Gradle:

``` 
gradlew bootRun
``` 

Or run it from Eclipse IDE:

``` 
gradlew cleanEclipse eclipse
``` 

Then run `Application.java` in `tut.webdata` package as Spring Boot App in Eclipse IDE.

When the app is running, go to <http://localhost:8090> and you should see homepage.