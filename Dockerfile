FROM openjdk:17-alpine
ARG JAR_FILE=music*.jar
COPY /target/${JAR_FILE} app.jar
EXPOSE 8085
CMD java -jar /app.jar
