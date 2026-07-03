package cl.duoc.empleados_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para empleados-service.
 *
 * Microservicio documentado por: Integrante 2 del equipo
 * (reemplazar "Integrante 2" por el nombre y apellido real antes de la
 * entrega; este nombre aparece en la cabecera de la documentacion).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI empleadoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Empleados Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 2. " +
                                "Administra al personal del restaurant: cargo, salario, " +
                                "turno y el local al que esta asignado cada empleado.")
                        .contact(new Contact()
                                .name("Integrante 2")
                                .email("integrante2@duoc.cl")));
    }
}
