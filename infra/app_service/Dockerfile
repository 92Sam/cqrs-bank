FROM openjdk:18.0

WORKDIR /app
ARG JAR_FILE=./target
COPY ${JAR_FILE} .
#RUN chmod +x ./target/bank-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["/bin/sh", "-c", "sleep 3600"]
#CMD ["java", "-jar", "/target/bank-service-0.0.1-SNAPSHOT.jar"]