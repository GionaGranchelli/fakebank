FROM openjdk:8-jdk-alpine
LABEL "com.fakebank.assignement"="Giona Granchelli"
LABEL com.fakebank.assignement="fakebank Backend"
LABEL version="1.0"
LABEL description="fakebank receives monthly deliveries of customer statement records.\
This information is delivered in two formats, CSV and XML. These records need to be validated"
EXPOSE 8080
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app
COPY target/*.jar /app/app.jar
COPY ./*.xsd /app/xsd/schema-records.xsd
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]
