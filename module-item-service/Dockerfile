# Stage 1: Build the project using Gradle
FROM gradle:7.6-jdk17 AS build

WORKDIR /app

# 프로젝트 소스 코드와 gradlew 파일을 복사
COPY gradlew ./
COPY gradle/ gradle/
COPY . .

# gradlew 실행 권한 추가
RUN chmod +x gradlew

# Gradle 빌드 실행
RUN ./gradlew :module-item-service:clean :module-item-service:build

# Stage 2: Run the application with a lightweight JRE
FROM openjdk:17-jdk-slim AS runtime

WORKDIR /fashion-mall/module-item-service

COPY --from=build /app/module-item-service/build/libs/*SNAPSHOT.jar app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]
