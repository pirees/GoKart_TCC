FROM openjdk:11

COPY target/goKart-0.0.1-SNAPSHOT.jar /app/app.jar

COPY /src/main/resources/bootsecurity.p12 /src/main/resources/bootsecurity.p12

CMD ["java", "-jar", "/app/app.jar"]

EXPOSE 8443