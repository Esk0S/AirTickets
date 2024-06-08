
FROM gradle:8.3-jdk17 AS builder

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY .env .

RUN gradle build

FROM openjdk:17-jdk

COPY --from=builder app/build/libs/AirTickets-0.0.1-SNAPSHOT.jar app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
