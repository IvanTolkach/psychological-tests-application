FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=default

ENTRYPOINT ["java","-jar","app.jar"]