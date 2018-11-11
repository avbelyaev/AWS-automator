FROM openjdk:jre-alpine

LABEL description="Standard JDK image with Automator"

COPY ./target/scala-2.12/automator.jar /app/
WORKDIR /app

ENTRYPOINT ["java", "-jar", "automator.jar"]
