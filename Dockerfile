FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/solta_server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /solta.jar
ENV USE_PROFILE dev

ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}", "-jar","/solta.jar"]