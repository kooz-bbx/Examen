package cl.duoc.envios_service.exception;

/**
 * CAPA: Service
 * Se lanza cuando se intenta crear un registro que ya existe
 * (por ejemplo, un email que ya está registrado).
 */
public class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
