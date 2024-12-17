FROM --platform=linux/amd64 gcr.io/distroless/java17

WORKDIR /fashion-mall/module-item-service

COPY module-item-service-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} module-item-service-0.0.1-SNAPSHOT.jar