FROM java:8-jdk-alpine

EXPOSE 8081

ADD target/card-main-app.jar card-main-app.jar

ENTRYPOINT ["java", "-jar", "/card-main-app.jar"]