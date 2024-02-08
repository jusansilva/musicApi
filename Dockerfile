FROM adoptopenjdk:17-jre-hotspot

WORKDIR /app

COPY musicapi/musicapi.jar app.jar

CMD ["java", "-jar", "app.jar"]
