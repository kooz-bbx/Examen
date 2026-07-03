package cl.duoc.eureka_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication //Activa Spring Boot completo.
@EnableEurekaServer //Convierte el proyecto en: servidor Eureka
public class Eureka {

    public static void main(String[] args) {

        SpringApplication.run(Eureka.class, args); //inicia la aplicación

    }
}