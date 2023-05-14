FROM openjdk:17
WORKDIR /app

COPY target/ebank-0.0.1-SNAPSHOT.jar build/app.jar
ENTRYPOINT ["java", "-jar", "build/app.jar"]