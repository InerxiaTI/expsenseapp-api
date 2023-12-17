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

# Imprimir los valores de las variables de construcción
RUN echo "PASS_BUILD_ARG=$PASS" && \
    echo "USER_BUILD_ARG=$USER" && \
    echo "HOST_BUILD_ARG=$HOST" && \
    echo "PORT_BUILD_ARG=$PORT" && \
    echo "DBNAME_BUILD_ARG=$DBNAME"


ENV PASS_DB=${PASS}
ENV USER_DB=${USER}
ENV DBNAME_DB=${DBNAME}
ENV HOST_DB=${HOST}
ENV PORT_DB=${PORT}

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