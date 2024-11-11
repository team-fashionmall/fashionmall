FROM openjdk:17-jdk-slim

WORKDIR /root/eureka

COPY module-eureka-server-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-eureka-server-0.0.1-SNAPSHOT.jar
