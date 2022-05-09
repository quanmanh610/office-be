FROM adoptopenjdk/openjdk11:alpine-jre

MAINTAINER BQ AN <bqan@cmcglobal.vn>

ADD build/libs/office-management-backend*.jar app.jar

RUN /bin/sh -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar"]