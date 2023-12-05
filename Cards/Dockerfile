#Variables
ARG owner=rdgpcampos.com
ARG jarFile=cards-0.0.1-SNAPSHOT.jar
ARG targetPath=target

#Start with a base image containing JVM
FROM openjdk:17-slim as build

#Image owner
MAINTAINER rdgpcampos.com

#Add the application's jar file to the container
COPY target/cards-0.0.1-SNAPSHOT.jar cards-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","/cards-0.0.1-SNAPSHOT.jar"]
