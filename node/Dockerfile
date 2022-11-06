FROM openjdk:11.0.7-jre-slim-buster
ARG JAR_FILE=target/node-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]