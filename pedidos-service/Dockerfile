# syntax=docker/dockerfile:1
#
# Dockerfile multi-stage estandar para los microservicios Spring Boot
# de RestaurantEnzo. Pensado para evitar el Error 429 de Maven Central:
# el pom.xml se copia y se resuelven las dependencias ANTES de copiar el
# codigo fuente, de modo que esa capa quede cacheada y Docker no vuelva
# a descargar todo el arbol de dependencias en cada build, salvo que el
# pom.xml cambie.
#
# IMPORTANTE: si en clases se entrego un Dockerfile oficial distinto a
# este, ese es el que debe usarse (no se debe modificar). Este archivo
# es un reemplazo funcional equivalente mientras no se cuente con el
# original.

# ===================== Etapa 1: build =====================
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app

# 1) Dependencias (capa cacheable)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# 2) Codigo fuente y empaquetado
COPY src ./src
RUN ./mvnw -B clean package -DskipTests

# ===================== Etapa 2: runtime =====================
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

# Usuario no-root por buenas practicas
RUN useradd -ms /bin/bash spring
USER spring

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
