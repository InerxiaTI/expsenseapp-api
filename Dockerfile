FROM adoptopenjdk/openjdk11:jdk-11.0.19_7-ubuntu-slim

WORKDIR /opt/app

# Copiar los archivos de la aplicación
COPY . .

# Definir las variables de entorno
ARG PASS
ARG USER
ARG DBNAME
ARG HOST
ARG PORT

ENV PASS_DB=${PASS}
ENV USER_DB=${USER}
ENV DBNAME_DB=${DBNAME}
ENV HOST_DB=${HOST}
ENV PORT_DB=${PORT}

CMD echo "El valor de PASS_DB es: $PASS_DB" && echo "El valor de USER_DB es: $USER_DB"
CMD echo "El valor de HOST_DB es: $HOST_DB" && echo "El valor de PORT_DB es: $PORT_DB"
CMD echo "El valor de DBNAME_DB es: $DBNAME_DB"

# Imprimir el valor de las variables de entorno
RUN echo "PASS_DB=$PASS_DB"
RUN echo "USER_DB=$USER_DB"
RUN echo "HOST_DB=$HOST_DB"
RUN echo "PORT_DB=$PORT_DB"
RUN echo "DBNAME_DB=$DBNAME_DB"

RUN chmod +x ./gradlew

# Compilar la aplicación
RUN ./gradlew clean build -x test

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/expense-mate-api.jar"]