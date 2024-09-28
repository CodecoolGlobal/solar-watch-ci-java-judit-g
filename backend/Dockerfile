#base image
FROM eclipse-temurin:21-jre-alpine

#main directory where the files are stored
WORKDIR /tmp

#copy jar file generated with mvn clean install
COPY target/*.jar app.jar

#command to start application
ENTRYPOINT ["java","-jar","app.jar"]