FROM maven:3.8.6-eclipse-temurin-17 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17.0.1_12-jdk

ENV POSTGRESQL_URL="jdbc:postgresql://db-transaction:5432/transaction" POSTGRESQL_USERNAME="postgres" POSTGRESQL_PASSWORD="postgres"

EXPOSE 8080
ENV TZ="America/Sao_Paulo"
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]