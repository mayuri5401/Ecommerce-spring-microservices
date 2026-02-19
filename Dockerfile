FROM eclipse-temurin:17
LABEL maintainer=""
WORKDIR /app
COPY target/*.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]