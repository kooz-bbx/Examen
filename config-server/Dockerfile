# syntax=docker/dockerfile:1
#
# Dockerfile del config-server. A diferencia de los demas microservicios,
# este necesita ademas la carpeta config-microservicios (modo "native" de
# Spring Cloud Config), por eso el build se hace con el contexto en la
# raiz del proyecto (ver docker-compose.yml -> context: .).
#
# IMPORTANTE: si en clases se entrego un Dockerfile oficial distinto a
# este, ese es el que debe usarse (no se debe modificar). Este archivo
# es un reemplazo funcional equivalente mientras no se cuente con el
# original.

# ===================== Etapa 1: build =====================
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app

COPY config-server/mvnw .
COPY config-server/.mvn .mvn
COPY config-server/pom.xml .
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

COPY config-server/src ./src
RUN ./mvnw -B clean package -DskipTests

# ===================== Etapa 2: runtime =====================
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

RUN useradd -ms /bin/bash spring
USER spring

COPY --from=build /app/target/*.jar app.jar
# Carpeta con la configuracion "remota" en modo nativo, en la misma
# ruta relativa (./config-microservicios) que se usa en local.
COPY config-microservicios ./config-microservicios

ENTRYPOINT ["java", "-jar", "app.jar"]
