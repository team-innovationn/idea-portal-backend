# Use an official Java runtime as a parent image
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file from the target directory
COPY target/idea-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "idea-0.0.1-SNAPSHOT.jar"]