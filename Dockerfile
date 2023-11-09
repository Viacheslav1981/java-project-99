FROM eclipse-temurin:20-jdk
FROM eclipse-temurin:20-jdk

ARG GRADLE_VERSION=8.4

RUN apt-get update && apt-get install -yq make unzip

COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradle --no-daemon dependencies

RUN ./gradle --no-daemon build

EXPOSE 8080

CMD java -jar build/libs/app-0.0.1-SNAPSHOT.jar

