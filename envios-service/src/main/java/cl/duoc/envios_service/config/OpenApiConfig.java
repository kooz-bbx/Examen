package cl.duoc.envios_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de OpenAPI / Swagger para envios-service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI envioServiceOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway");
        return new OpenAPI()
                .servers(List.of(gatewayServer))
                .info(new Info()
                        .title("Envios Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description("Administra los envios asociados a los pedidos del restaurant: " +
                                "direccion, estado y fecha de despacho."));
    }
}
