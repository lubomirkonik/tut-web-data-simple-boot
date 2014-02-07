Web application based on tutorials http://spring.io/guides/tutorials/data/, http://spring.io/guides/tutorials/web and http://www.thymeleaf.org/layouts.html
with Spring Boot in a more simple way.
Registration and login added from Layouts project. 
It uses MongoDB document store, H2 relational database and Gemfire data grid store.

Project is only for learning purposes, it doesn't follow principles of Hexagonal Architecture.

Start application as follows:
MongoDB: 
"\<path\>\bin\mongod.exe" --dbpath "\<path\>\data" 
"\<path\>\bin\mongo.exe"

Gemfire (not used at master1-layouts branch): 
.\gradlew run
 
Run Application.class with main method in config package.