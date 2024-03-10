FROM openjdk:17-jdk
ARG JAR_FILE=impl/target/network-impl-1.0.0-SNAPSHOT-spring-boot.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


