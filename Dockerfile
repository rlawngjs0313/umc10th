FROM eclipse-temurin:21-jdk
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} umc10th.jar
RUN --mount=type=secret,id=env \
  cp /run/secrets/env /app/.env

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "umc10th.jar"]