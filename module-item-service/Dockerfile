# Build stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /fashion-mall/module-item-service

COPY . .

RUN ./mvnw clean package -DskipTests

# Run stage with distroless image
FROM gcr.io/distroless/java:17

WORKDIR /fashion-mall/module-item-service

COPY --from=build /fashion-mall/module-item-service/target/*SNAPSHOT.jar .

CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "*SNAPSHOT.jar"]