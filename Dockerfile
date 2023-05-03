FROM openjdk:13-alpine

EXPOSE 8081

ADD target/shumov-cloud-0.0.1-SNAPSHOT.jar shumov-cloud.jar

ENTRYPOINT ["java", "-jar", "/shumov-cloud.jar"]