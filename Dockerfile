FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/solta_server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /solta.jar

ENTRYPOINT ["java","-jar","/solta.jar"]