FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew :attempts-service:bootJar -x test --no-daemon

FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/attempts-service/build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]
