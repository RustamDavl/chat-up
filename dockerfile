FROM openjdk:17
COPY target/ChatUpp-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/ChatUpp-0.0.1-SNAPSHOT.jar"]