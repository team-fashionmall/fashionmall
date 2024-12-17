# Build stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /fashion-mall/module-item-service

COPY . .

# Gradle Wrapper에 실행 권한을 부여
RUN chmod +x gradlew

# Gradle 빌드 실행 (테스트 제외)
RUN ./gradlew clean build -x test

# Run stage with openjdk:17-slim
FROM openjdk:17-slim

WORKDIR /fashion-mall/module-item-service

# 빌드된 JAR 파일을 정확한 이름으로 복사 (Gradle 빌드 후 JAR 파일은 build/libs/에 위치)
COPY --from=build /fashion-mall/module-item-service/build/libs/module-item-service-0.0.1-SNAPSHOT.jar .

# 정확한 JAR 파일 이름을 사용하여 실행
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "module-item-service-0.0.1-SNAPSHOT.jar"]
