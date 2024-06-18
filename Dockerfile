# Stage 1: Build the application using Maven
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
# Copying pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .
# Only download dependencies if the pom.xml file has changed
RUN mvn dependency:go-offline
COPY src ./src
# Package without running tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Run the application using the JDK image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Build arguments for environment-specific configuration
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG PROFILE

# Set environment variables for database connection
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL} \
    SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME} \
    SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD} \
    PROFILE=${PROFILE}

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Non-root User for security
# Create a group and user
RUN groupadd -r spring && useradd -r -g spring spring
# Change to the non-root user
USER spring

# Expose the application's port
EXPOSE 8080

# Healthcheck to ensure container is healthy
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Use the shell form of ENTRYPOINT to use environment variables
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${PROFILE} -jar /app.jar"]
