package cl.duoc.locales_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de OpenAPI / Swagger para locales-service.
 *
 * Microservicio documentado por: Integrante 2 del equipo
 * (reemplazar "Integrante 2" por el nombre y apellido real antes de la
 * entrega; este nombre aparece en la cabecera de la documentacion).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI localServiceOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway");
        return new OpenAPI()
                .servers(List.of(gatewayServer))
                .info(new Info()
                        .title("Locales Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 2. " +
                                "Administra las sucursales (locales) del restaurant: " +
                                "direccion, comuna, horario y estado (activo/inactivo).")
                        .contact(new Contact()
                                .name("Integrante 2")
                                .email("integrante2@duoc.cl")));
    }
}
