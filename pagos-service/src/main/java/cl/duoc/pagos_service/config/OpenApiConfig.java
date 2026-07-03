package cl.duoc.pagos_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para pagos-service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pagoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pagos Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description("Administra los pagos asociados a los pedidos del restaurant."));
    }
}
