FROM openjdk:17-jdk-slim

WORKDIR /root/item

COPY module-item-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-item-service-0.0.1-SNAPSHOT.jar
