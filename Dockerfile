FROM adoptopenjdk/maven-openjdk11:latest as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM adoptopenjdk/openjdk11
COPY --from=builder /app/target/*.jar /app/application.jar
ENTRYPOINT ["java", "-jar","app/application.jar"]