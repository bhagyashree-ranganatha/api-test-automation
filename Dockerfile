# Use the official Maven image with Java 17
FROM maven:3.9.4-eclipse-temurin-17

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# The default command: Run the tests when the container starts
CMD ["mvn", "clean", "test"]