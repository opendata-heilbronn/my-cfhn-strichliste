FROM maven:3-jdk-10 AS builder
WORKDIR /usr/src/app
COPY src ./src
COPY pom.xml ./pom.xml
RUN mvn clean install

FROM openjdk:10
ENV JAVA_OPTS=""
WORKDIR /usr/share/app
COPY docker/run.sh ./run.sh
COPY --from=builder /usr/src/app/target/strichliste-ms-*.jar ./app.jar
CMD ["/usr/share/app/run.sh"]