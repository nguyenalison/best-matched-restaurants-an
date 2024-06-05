# Use an official Maven image to build the app
FROM maven:latest AS build
WORKDIR /org.alisonnguyen
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim
WORKDIR /org.alisonnguyen
COPY --from=build /org.alisonnguyen/target/*.jar best-matched-restaurants-an.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "best-matched-restaurants-an.jar"]