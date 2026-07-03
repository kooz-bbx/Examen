package cl.duoc.pedidos_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de OpenAPI / Swagger para pedidos-service.
 *
 * Microservicio documentado por: Integrante 3 del equipo
 * (reemplazar "Integrante 3" por el nombre y apellido real antes de la
 * entrega; este nombre aparece en la cabecera de la documentacion).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pedidoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pedidos Service - RestaurantEnzo")
                        .version("1.0.0")
                        .description(
                                "Documentado por: Integrante 3. " +
                                "Gestiona los pedidos de los clientes (detalle, total, " +
                                "estado) y se comunica con clientes-service via Feign " +
                                "para validar y enriquecer cada pedido con los datos " +
                                "del cliente.")
                        .contact(new Contact()
                                .name("Integrante 3")
                                .email("integrante3@duoc.cl")));
    }
}
