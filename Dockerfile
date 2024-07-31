FROM openjdk:17-alpine

WORKDIR /app

COPY /target/oeuvre-api-0.0.1-SNAPSHOT.jar /app/oeuvre-api.jar
COPY .env /app/.env
EXPOSE 8080
CMD ["java", "-jar", "oeuvre-api.jar"]
