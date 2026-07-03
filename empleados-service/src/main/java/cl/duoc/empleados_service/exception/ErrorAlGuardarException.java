package cl.duoc.empleados_service.exception;

/**
 * CAPA: Repository
 * Se lanza cuando ocurre un error al intentar guardar o actualizar
 * un registro en la base de datos.
 */
public class ErrorAlGuardarException extends RuntimeException {
    public ErrorAlGuardarException(String mensaje) {
        super(mensaje);
    }
}
