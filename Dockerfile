# Multi-stage Dockerfile for HelloJenkins Java Console Application

# Stage 1: Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .

# Create the Maven directory structure
RUN mkdir -p src/main/java/com/jenkins src/test/java/com/jenkins

# Copy source files to proper Maven structure
COPY main.java src/main/java/com/jenkins/HelloJenkins.java
COPY mainTest.java src/test/java/com/jenkins/HelloJenkinsTest.java

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Build the application and run tests
RUN mvn clean package

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the JAR file from builder stage
COPY --from=builder /app/target/*.jar hello-jenkins.jar

# Change ownership to non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Set JVM options for containerized environment (lightweight for console app)
ENV JAVA_OPTS="-Xmx128m -Xms64m -XX:+UseG1GC -XX:+UseContainerSupport"

# Add labels for better container management
LABEL maintainer="Jenkins CI/CD Demo"
LABEL description="HelloJenkins Java Console Application"
LABEL version="1.0"

# Run the HelloJenkins application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -cp hello-jenkins.jar com.jenkins.HelloJenkins"]