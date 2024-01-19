FROM openjdk:18.0-slim
COPY build/libs/Grades-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]