FROM eclipse-temurin:21-jre-alpine
COPY target/gestao-reserva-0.0.1-SNAPSHOT.jar /app/gestao-reserva.jar
WORKDIR /app
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "gestao-reserva.jar"]