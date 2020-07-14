FROM java:8-jdk-alpine

EXPOSE 8080

ADD target/main-app.jar main-app.jar

ENTRYPOINT ["java", "-jar", "/main-app.jar"]