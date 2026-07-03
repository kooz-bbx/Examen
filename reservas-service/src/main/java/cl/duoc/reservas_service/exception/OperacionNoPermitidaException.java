package cl.duoc.reservas_service.exception;

/**
 * CAPA: Service
 * Se lanza cuando se intenta realizar una operación que las reglas
 * de negocio no permiten (por ejemplo, eliminar un registro con dependencias).
 */
public class OperacionNoPermitidaException extends RuntimeException {
    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
