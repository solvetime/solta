FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/solta_server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /solta.jar

ENTRYPOINT ["java","-jar","/solta.jar"]