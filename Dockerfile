FROM openjdk:8-jre-alpine
COPY target/main-project-0.0.1-SNAPSHOT.jar /service.jar
CMD /usr/bin/java -jar /service.jar
