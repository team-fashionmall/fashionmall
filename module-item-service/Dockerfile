# Stage 1: Build the JAR file
FROM gradle:7.6-jdk17 AS build
WORKDIR /app

# 프로젝트 소스 코드 복사 및 빌드 실행
COPY . .
RUN ./gradlew :module-item-service:clean :module-item-service:build

# Stage 2: Run the application with a lightweight JRE
FROM openjdk:17-jre-slim
WORKDIR /fashion-mall/module-item-service

# 빌드된 JAR 파일을 복사
COPY --from=build /app/module-item-service/build/libs/*SNAPSHOT.jar app.jar

# Spring 프로파일 활성화
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]