package cl.duoc.autenticacion_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para autenticacion-service.
 *
 * Microservicio documentado por: Integrante 1 del equipo
 * (reemplazar "Integrante 1" por el nombre y apellido real en este
 * archivo antes de la entrega; este nombre aparece en la cabecera de
 * la documentacion Swagger, tal como exige la pauta).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuarioServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Autenticacion Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 1. " +
                                "Gestiona el alta, consulta y validacion de usuarios " +
                                "(credenciales y rol) usados para autenticar el acceso " +
                                "al ecosistema de RestaurantEnzo.")
                        .contact(new Contact()
                                .name("Integrante 1")
                                .email("integrante1@duoc.cl")));
    }
}
