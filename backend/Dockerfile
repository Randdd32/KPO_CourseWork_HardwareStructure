FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
RUN apt-get update && apt-get install -y fonts-dejavu-core && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/target/hardware-structure-*.jar /app/hardware-structure.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hardware-structure.jar"]
