FROM openjdk:17-jdk-slim

WORKDIR /root/image

COPY module-image-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-image-service-0.0.1-SNAPSHOT.jar
