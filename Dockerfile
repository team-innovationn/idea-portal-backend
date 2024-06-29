# Use a base image with JDK
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/myapp.jar /app/myapp.jar

# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "myapp.jar"]