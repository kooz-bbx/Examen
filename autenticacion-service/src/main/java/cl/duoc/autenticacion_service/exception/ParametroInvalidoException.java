package cl.duoc.autenticacion_service.exception;

/**
 * CAPA: Controller
 * Se lanza cuando el cliente envía un parámetro con formato incorrecto
 * (por ejemplo, un texto donde se esperaba un número).
 */
public class ParametroInvalidoException extends RuntimeException {
    public ParametroInvalidoException(String mensaje) {
        super(mensaje);
    }
}
