package cl.duoc.productos_service.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para productos-service.
 *
 * Microservicio documentado por: Integrante 3 del equipo
 * (reemplazar "Integrante 3" por el nombre y apellido real antes de la
 * entrega; este nombre aparece en la cabecera de la documentacion).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Productos Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 3. " +
                                "Administra el catalogo de productos del restaurant: " +
                                "precio, stock, categoria y disponibilidad.")
                        .contact(new Contact()
                                .name("Integrante 3")
                                .email("integrante3@duoc.cl")));
    }
}
