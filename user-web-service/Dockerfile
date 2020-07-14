FROM java:8-jdk-alpine

EXPOSE 8081

ADD target/user-web-service.jar user-web-service.jar

ENTRYPOINT ["java", "-jar", "/user-web-service.jar"]