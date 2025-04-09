FROM maven:3.8.7-openjdk-18-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar",  "app.jar"]