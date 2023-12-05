FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

COPY . .

RUN mvn clean install -DskipTests

FROM adoptopenjdk:17-jdk-hotspot

WORKDIR /app

COPY --from=build /app/target/customer-container-1.0-SNAPSHOT.jar .