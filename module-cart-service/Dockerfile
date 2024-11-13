FROM openjdk:17-jdk-slim

WORKDIR /fashion-mall/module-cart-service

COPY *SNAPSHOT.jar .

CMD java -jar -DJWT_SECRET_KEY=${{ secrets.JWT_SECRET_KEY }} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} *SNAPSHOT.jar
