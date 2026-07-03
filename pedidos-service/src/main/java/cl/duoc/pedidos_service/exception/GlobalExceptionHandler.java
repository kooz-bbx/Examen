package cl.duoc.pedidos_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================================
    // CAPA CONTROLLER
    // =========================================================

    // NUEVO: Manejador para los errores de validación de Spring (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        Map<String, Object> body = new java.util.HashMap<>();
        body.put("timestamp", java.time.LocalDateTime.now().toString());
        body.put("status", org.springframework.http.HttpStatus.BAD_REQUEST.value());
        body.put("error", "Error de validación");
        body.put("mensaje", mensaje);

        return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ParametroInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleParametroInvalido(ParametroInvalidoException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Parámetro inválido", ex.getMessage());
    }

    @ExceptionHandler(MetodoNoPermitidoException.class)
    public ResponseEntity<Map<String, Object>> handleMetodoNoPermitido(MetodoNoPermitidoException ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Método no permitido", ex.getMessage());
    }

    @ExceptionHandler(RutaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleRutaNoEncontrada(RutaNoEncontradaException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Ruta no encontrada", ex.getMessage());
    }

    // =========================================================
    // CAPA SERVICE
    // =========================================================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Registro no encontrado", ex.getMessage());
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicado(RegistroDuplicadoException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Registro duplicado", ex.getMessage());
    }

    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<Map<String, Object>> handleOperacionNoPermitida(OperacionNoPermitidaException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Operación no permitida", ex.getMessage());
    }

    @ExceptionHandler(DatosInsuficientesException.class)
    public ResponseEntity<Map<String, Object>> handleDatosInsuficientes(DatosInsuficientesException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Datos insuficientes", ex.getMessage());
    }

    // =========================================================
    // CAPA REPOSITORY
    // =========================================================

    @ExceptionHandler(ErrorAlGuardarException.class)
    public ResponseEntity<Map<String, Object>> handleErrorAlGuardar(ErrorAlGuardarException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar en base de datos", ex.getMessage());
    }

    @ExceptionHandler(ErrorDeConexionException.class)
    public ResponseEntity<Map<String, Object>> handleErrorConexion(ErrorDeConexionException ex) {
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, "Error de conexión con la base de datos", ex.getMessage());
    }

    @ExceptionHandler(IntegridadDeDatosException.class)
    public ResponseEntity<Map<String, Object>> handleIntegridad(IntegridadDeDatosException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Violación de integridad de datos", ex.getMessage());
    }

    // =========================================================
    // ERROR GENERAL (captura cualquier excepción no prevista)
    // =========================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getMessage());
    }

    // =========================================================
    // MÉTODO AUXILIAR
    // =========================================================

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(body);
    }
}
