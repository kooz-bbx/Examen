package cl.duoc.clientes_service.Exception;

/**
 * CAPA: Repository
 * Se lanza cuando se viola una restricción de integridad en la base de datos
 * (por ejemplo, clave foránea inválida o campo único duplicado a nivel de BD).
 */
public class IntegridadDeDatosException extends RuntimeException {
    public IntegridadDeDatosException(String mensaje) {
        super(mensaje);
    }
}
