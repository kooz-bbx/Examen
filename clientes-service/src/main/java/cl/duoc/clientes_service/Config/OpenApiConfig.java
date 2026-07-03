package cl.duoc.clientes_service.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para clientes-service.
 *
 * Microservicio documentado por: Integrante 1 del equipo
 * (reemplazar "Integrante 1" por el nombre y apellido real antes de la
 * entrega; este nombre aparece en la cabecera de la documentacion).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clienteServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clientes Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 1. " + "Administra los datos de los clientes del restaurant " + "(nombre, contacto, ciudad) usados por pedidos y reservas.")
                        .contact(new Contact()
                                .name("Integrante 1")
                                .email("integrante1@duoc.cl")));
    }
}
