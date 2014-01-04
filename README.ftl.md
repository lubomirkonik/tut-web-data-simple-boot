Web application based on tutorials http://spring.io/guides/tutorials/data/ and http://spring.io/guides/tutorials/web 
with Spring Boot in a more simple way.
It uses MongoDB document store, H2 relational database and Gemfire data grid store.

Project is only for learning purposes, it doesn't follow principles of Hexagonal Architecture.

Start application as follows:
MongoDB
path\mongod.exe --dbpath path\data
path\mongo.exe

Gemfire
.\gradlew run

Run Application class with main method in config package.