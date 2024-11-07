FROM openjdk:17-jdk-slim

WORKDIR /root

COPY ./build/libs/module-gateway-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-gateway-service-0.0.1-SNAPSHOT.jar
