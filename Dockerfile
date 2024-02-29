FROM openjdk:17
COPY impl/target/network-impl-1.0.0-SNAPSHOT-spring-boot.jar app.jar
ENTRYPOINT ["java","-Dliquibase.preserveSchemaCase=true", "-jar","/app.jar", "--spring.profiles.active=local"]