FROM eclipse-temurin:17

LABEL maintainer="mendozavincent444@gmail.com"

WORKDIR /app

COPY target/server-application-0.0.1-SNAPSHOT.jar /app/server-application.jar

ENTRYPOINT ["java", "-jar", "server-application.jar"]