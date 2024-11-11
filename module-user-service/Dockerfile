FROM openjdk:17-jdk-slim

WORKDIR /root/user

COPY module-user-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-user-service-0.0.1-SNAPSHOT.jar
