FROM openjdk:17-alpine

WORKDIR /fashion-mall/module-item-service

COPY *SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} *SNAPSHOT.jar
