# Stage 1: build
# Start with a Maven image that includes JDK17
FROM maven:3.9.8-amazoncorretto-17 AS build

# Copy source code and pom.xml file to /app forder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with mave
RUN mvn package -DskipTests

# Stage 2: Create image
# Start with Amazon Corretto JDK 17
FROM amazoncorretto:17.0.12

# Set working forder to App and complied file above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Comment to run application
ENTRYPOINT ["java", "-jar", "app.jar"]




