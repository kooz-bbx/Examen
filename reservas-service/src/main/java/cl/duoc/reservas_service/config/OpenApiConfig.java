package cl.duoc.reservas_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de OpenAPI / Swagger para reservas-service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reservaServiceOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway");

        return new OpenAPI()
                .servers(List.of(gatewayServer))
                .info(new Info()
                        .title("Reservas Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description("Administra las reservas de mesa de los clientes en los locales del restaurant."));
    }
}
