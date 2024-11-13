FROM openjdk:17-jdk-slim

WORKDIR /fashion-mall/module-cart-service

ARG JWT_SECRET_KEY

ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}

COPY *SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Djwt.secret.key=${JWT_SECRET_KEY} *SNAPSHOT.jar