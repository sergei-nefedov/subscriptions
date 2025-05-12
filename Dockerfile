# Этап сборки
FROM maven:3.9-eclipse-temurin-17-alpine as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package -DskipTests

# Финальный образ
FROM openjdk:17-alpine
COPY --from=builder /app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]