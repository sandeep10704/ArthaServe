# Use Java 21 (Debian-based)
FROM openjdk:21-jdk

WORKDIR /app

COPY target/shoplite-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
