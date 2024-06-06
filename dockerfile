# Use an official Java runtime as a parent image
FROM openjdk:18

# Set the working directory in the container
WORKDIR /usr/src/bootapp

# Copy the application JAR file into the container at the working directory
COPY target/Assignment-0.0.1-SNAPSHOT.jar .

# Expose the port your application listens on
EXPOSE 8017

# Specify the command to run your application
CMD ["java", "-jar", "Assignment-0.0.1-SNAPSHOT.jar"]
