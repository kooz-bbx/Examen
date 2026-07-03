package cl.duoc.reservas_service.exception;
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) { super(mensaje); }
}
