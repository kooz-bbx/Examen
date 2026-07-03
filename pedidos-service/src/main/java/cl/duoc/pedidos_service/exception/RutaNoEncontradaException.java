package cl.duoc.pedidos_service.exception;

/**
 * CAPA: Controller
 * Se lanza cuando se intenta acceder a una ruta que no existe en este servicio.
 */
public class RutaNoEncontradaException extends RuntimeException {
    public RutaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
