FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/hardware-structure-*.jar /app/hardware-structure.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hardware-structure.jar"]
