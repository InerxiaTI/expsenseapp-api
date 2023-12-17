FROM adoptopenjdk/openjdk11:jdk-11.0.19_7-ubuntu-slim

WORKDIR /opt/app

# Copiar los archivos de la aplicación
COPY . .

# Definir las variables de entorno
ARG PASS
ARG DB_USER_EXPENSE
ARG DBNAME
ARG HOST
ARG PORT

ENV PASS=${PASS}
ENV USER=${DB_USER_EXPENSE}
ENV DBNAME=${DBNAME}
ENV HOST=${DB_HOST_EXPENSE}
ENV PORT=${PORT}

# Imprimir el valor de las variables de entorno
RUN echo "PASS_DB=$PASS"
RUN echo "USER_DB=$USER"
RUN echo "HOST_DB=$HOST"
RUN echo "PORT_DB=$PORT"
RUN echo "DBNAME_DB=$DBNAME"

RUN chmod +x ./gradlew

# Compilar la aplicación
RUN ./gradlew clean build -x test

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/expense-mate-api.jar"]