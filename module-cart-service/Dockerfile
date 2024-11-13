FROM openjdk:17-jdk-slim

WORKDIR /fashion-mall/module-cart-service

COPY *SNAPSHOT.jar .

ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}

CMD ["sh", "-c", "java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Djwt.secret.key=${JWT_SECRET_KEY} *SNAPSHOT.jar"]