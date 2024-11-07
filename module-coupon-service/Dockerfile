FROM openjdk:17-jdk-slim

WORKDIR /root

COPY ./build/libs/module-coupon-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-coupon-service-0.0.1-SNAPSHOT.jar
