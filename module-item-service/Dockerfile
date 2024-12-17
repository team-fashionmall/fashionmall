# Build stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /fashion-mall/module-item-service

COPY . .

RUN ./mvnw clean package -DskipTests

# Run stage with openjdk:17-slim
FROM openjdk:17-slim

WORKDIR /fashion-mall/module-item-service

# 빌드된 JAR 파일을 정확한 이름으로 복사
COPY --from=build /fashion-mall/module-item-service/target/module-item-service-0.0.1-SNAPSHOT.jar .

# 정확한 JAR 파일 이름을 사용하여 실행
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "module-item-service-0.0.1-SNAPSHOT.jar"]
