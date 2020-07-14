FROM java:8-jdk-alpine

EXPOSE 8085

ADD target/play-web-service.jar play-web-service.jar

ENTRYPOINT ["java", "-jar", "/play-web-service.jar"]