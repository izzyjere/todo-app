# Start with a base image containing Java runtime
FROM openjdk:21-slim as build

LABEL author="Wisdom Jere"

# Set the location of the app
WORKDIR /app

# Add the application's jar to the container
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Run the application
FROM openjdk:21-slim
COPY --from=build /app/target/todo-app-0.0.1-SNAPSHOT.jar todo-app.jar
ENTRYPOINT ["java","-jar","/todo-app.jar"]
