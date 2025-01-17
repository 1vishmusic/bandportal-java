FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=build/libs/bandportal.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]