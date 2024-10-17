FROM openjdk:17-jdk
WORKDIR /app
COPY application/app-service/build/libs/app-service.jar app.jar
COPY .env .env
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
