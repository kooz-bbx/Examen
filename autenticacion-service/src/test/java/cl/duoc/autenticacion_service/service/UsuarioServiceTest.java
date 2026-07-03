package cl.duoc.autenticacion_service.service;

import cl.duoc.autenticacion_service.dto.UsuarioDTO;
import cl.duoc.autenticacion_service.exception.ResourceNotFoundException;
import cl.duoc.autenticacion_service.mapper.UsuarioMapper;
import cl.duoc.autenticacion_service.model.Usuario;
import cl.duoc.autenticacion_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de la capa @Service (UsuarioService).
 * Autor: Integrante 1.
 *
 * Idea simple: NO se conecta a una base de datos real. El repositorio y
 * el mapper se simulan (mock) con Mockito, asi probamos solo la LOGICA
 * de UsuarioService de forma aislada y rapida.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - capa Service")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .username("jperez")
                .password("clave123")
                .rol("ADMIN")
                .build();

        usuarioDTO = UsuarioDTO.builder()
                .id(1L)
                .username("jperez")
                .password("clave123")
                .rol("ADMIN")
                .build();
    }

    @Test
    @DisplayName("listar() retorna todos los usuarios mapeados a DTO")
    void listar_retornaListaDeUsuarios() {
        when(repository.findAll()).thenReturn(List.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(usuarioDTO);

        List<UsuarioDTO> resultado = usuarioService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("jperez", resultado.get(0).getUsername());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("buscarPorId() retorna el usuario cuando existe")
    void buscarPorId_usuarioExiste_retornaDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    @DisplayName("buscarPorId() lanza excepcion cuando el usuario no existe")
    void buscarPorId_usuarioNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> usuarioService.buscarPorId(99L)
        );
        assertTrue(exception.getMessage().contains("99"));
    }

    @Test
    @DisplayName("guardar() persiste el usuario y retorna el DTO creado")
    void guardar_creaUsuarioCorrectamente() {
        when(mapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.guardar(usuarioDTO);

        assertEquals("jperez", resultado.getUsername());
        verify(repository, times(1)).save(usuario);
    }

    @Test
    @DisplayName("eliminar() borra el usuario cuando existe")
    void eliminar_usuarioExiste_eliminaCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() lanza excepcion cuando el usuario no existe")
    void eliminar_usuarioNoExiste_lanzaExcepcion() {
        when(repository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> usuarioService.eliminar(50L)
        );
        verify(repository, never()).deleteById(any());
    }
}
