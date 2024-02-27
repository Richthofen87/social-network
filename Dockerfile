FROM openjdk:17
COPY impl/target/*.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar", "--spring.config.location=classpath:/application.yml,classpath:/application-local.yml"]