FROM adoptopenjdk/openjdk11:latest

EXPOSE 8080 8081

RUN mkdir app

COPY target/2025_Sri_Guru_Aakash_Darsi_BE-1.0-SNAPSHOT.jar app
COPY config.yml app

WORKDIR app

CMD ["java","-jar","2025_Sri_Guru_Aakash_Darsi_BE-1.0-SNAPSHOT.jar","server","config.yml"]
