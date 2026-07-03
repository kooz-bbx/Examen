package cl.duoc.productos_service.Exception;

/**
 * CAPA: Repository
 * Se lanza cuando no se puede establecer conexión con la base de datos.
 */
public class ErrorDeConexionException extends RuntimeException {
    public ErrorDeConexionException(String mensaje) {
        super(mensaje);
    }
}
