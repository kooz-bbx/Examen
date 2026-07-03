package cl.duoc.inventario_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para inventario-service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventario Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description("Administra el stock de productos por local del restaurant."));
    }
}
