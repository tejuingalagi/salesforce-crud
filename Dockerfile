# Build the React frontend
FROM node:20 AS frontend-build
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ ./
RUN npm run build

# Build the Spring Boot backend
FROM maven:3.9-eclipse-temurin-21 AS backend-build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY --from=frontend-build /frontend/dist ./src/main/resources/static
RUN mvn -q -DskipTests package

# Run the application
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=backend-build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
