FROM openjdk:11.0.13-jre-slim-buster

MAINTAINER Lucas Bovolini lbovolini94@gmail.com

ENV APP_NAME=estabelecimento
ENV APP_FOLDER=~/estabelecimento

RUN addgroup estabelecimento && adduser --ingroup estabelecimento estabelecimento
USER estabelecimento:estabelecimento

WORKDIR ${APP_FOLDER}

COPY "./target/${APP_NAME}*.jar" ${APP_NAME}.jar

ENTRYPOINT java -jar ${APP_NAME}.jar