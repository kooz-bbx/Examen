package cl.duoc.autenticacion_service.exception;

/**
 * CAPA: Service
 * Se lanza cuando faltan campos obligatorios para completar la operación.
 */
public class DatosInsuficientesException extends RuntimeException {
    public DatosInsuficientesException(String mensaje) {
        super(mensaje);
    }
}
