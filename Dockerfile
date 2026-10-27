FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /workspace
COPY settings.gradle build.gradle gradle.properties ./
COPY src ./src
RUN gradle --no-daemon clean test bootJar

FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --uid 10001 appuser
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
