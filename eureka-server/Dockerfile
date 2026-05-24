FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew :eureka-server:bootJar -x test --no-daemon

FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/eureka-server/build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=default
ENTRYPOINT ["java", "-jar", "app.jar"]
