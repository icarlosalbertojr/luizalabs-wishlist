### Build stage
FROM gradle:8.0-jdk17-alpine AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

### Release stage
FROM openjdk:17-alpine AS release

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]