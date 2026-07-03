package cl.duoc.autenticacion_service.controller;

import cl.duoc.autenticacion_service.dto.UsuarioDTO;
import cl.duoc.autenticacion_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Autor: Integrante 1 (reemplazar por el nombre real antes de la entrega).
 */
@Tag(name = "Usuarios", description = "Autor: Integrante 1 - Administracion de usuarios y credenciales")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)),
                            examples = @ExampleObject(name = "Listado de usuarios", value = """
                                    [
                                      {"id": 1, "username": "jsoto", "password": "P@ssw0rd123", "rol": "ADMIN"}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @Operation(summary = "Buscar usuario por id", description = "Retorna un usuario segun su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioDTO.class),
                            examples = @ExampleObject(name = "Usuario encontrado", value = """
                                    {"id": 1, "username": "jsoto", "password": "P@ssw0rd123", "rol": "ADMIN"}"""))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Usuario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Usuario no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario (username, password y rol).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioDTO.class),
                            examples = @ExampleObject(name = "Usuario creado", value = """
                                    {"id": 1, "username": "jsoto", "password": "P@ssw0rd123", "rol": "ADMIN"}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El username no puede estar vacio."}""")))
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardar(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.guardar(dto));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioDTO.class),
                            examples = @ExampleObject(name = "Usuario actualizado", value = """
                                    {"id": 1, "username": "jsoto", "password": "NuevaClave456", "rol": "EMPLEADO"}"""))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Usuario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Usuario no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Usuario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Usuario no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
