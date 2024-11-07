FROM openjdk:17-jdk-slim

WORKDIR /root

COPY ./build/libs/module-item-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${active} module-item-service-0.0.1-SNAPSHOT.jar
