FROM openjdk:17-jdk-slim

WORKDIR /fashion-mall

COPY ../../.. .

RUN ./gradlew claen build

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-cart-service-0.0.1-SNAPSHOT.jar