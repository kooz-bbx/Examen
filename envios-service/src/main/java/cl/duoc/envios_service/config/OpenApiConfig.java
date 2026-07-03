package cl.duoc.envios_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para envios-service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI envioServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Envios Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description("Administra los envios asociados a los pedidos del restaurant: " +
                                "direccion, estado y fecha de despacho."));
    }
}
