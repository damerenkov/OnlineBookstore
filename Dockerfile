FROM openjdk:20
COPY target/OnlineBookstore-1.0-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "/backend.jar"]