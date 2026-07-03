package cl.duoc.productos_service.Exception;

/**
 * CAPA: Controller
 * Se lanza cuando se usa un método HTTP no soportado en ese endpoint
 * (por ejemplo, DELETE donde solo se permite GET).
 */
public class MetodoNoPermitidoException extends RuntimeException {
    public MetodoNoPermitidoException(String mensaje) {
        super(mensaje);
    }
}
