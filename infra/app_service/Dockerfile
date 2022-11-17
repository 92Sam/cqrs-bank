FROM openjdk:18.0
EXPOSE 8080
WORKDIR /app
ARG JAR_FILE=/target/bank-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ADD .env data.env
RUN chmod +x app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]