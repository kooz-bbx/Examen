package cl.duoc.locales_service.service;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.exception.ResourceNotFoundException;
import cl.duoc.locales_service.mapper.LocalMapper;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.repository.LocalRepository;
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
 * Pruebas unitarias de la capa @Service (LocalService).
 * Autor: Integrante 2.
 *
 * Se mockean LocalRepository y LocalMapper para aislar la logica de
 * LocalService de la base de datos real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LocalService - capa Service")
class LocalServiceTest {

    @Mock
    private LocalRepository repository;

    @Mock
    private LocalMapper mapper;

    @InjectMocks
    private LocalService localService;

    private Local local;
    private LocalDTO localDTO;

    @BeforeEach
    void setUp() {
        local = Local.builder()
                .id(1L).nombre("Enzo Providencia").direccion("Av. Providencia 123")
                .comuna("Providencia").horario("12:00-23:00").activo(true)
                .build();

        localDTO = LocalDTO.builder()
                .id(1L).nombre("Enzo Providencia").direccion("Av. Providencia 123")
                .comuna("Providencia").horario("12:00-23:00").activo(true)
                .build();
    }

    @Test
    @DisplayName("listar() retorna todos los locales mapeados")
    void listar_retornaListado() {
        when(repository.findAll()).thenReturn(List.of(local));
        when(mapper.toDTO(local)).thenReturn(localDTO);

        List<LocalDTO> resultado = localService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Providencia", resultado.get(0).getComuna());
    }

    @Test
    @DisplayName("buscarPorId() retorna el local cuando existe")
    void buscarPorId_localExiste_retornaDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(local));
        when(mapper.toDTO(local)).thenReturn(localDTO);

        LocalDTO resultado = localService.buscarPorId(1L);

        assertEquals("Enzo Providencia", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId() lanza excepcion cuando el local no existe")
    void buscarPorId_localNoExiste_lanzaExcepcion() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> localService.buscarPorId(404L));
    }

    @Test
    @DisplayName("guardar() persiste y retorna el local creado")
    void guardar_creaLocalCorrectamente() {
        when(mapper.toEntity(localDTO)).thenReturn(local);
        when(repository.save(local)).thenReturn(local);
        when(mapper.toDTO(local)).thenReturn(localDTO);

        LocalDTO resultado = localService.guardar(localDTO);

        assertEquals("Av. Providencia 123", resultado.getDireccion());
        verify(repository, times(1)).save(local);
    }

    @Test
    @DisplayName("buscarActivos() retorna solo los locales activos")
    void buscarActivos_retornaSoloActivos() {
        when(repository.findByActivo(true)).thenReturn(List.of(local));
        when(mapper.toDTO(local)).thenReturn(localDTO);

        List<LocalDTO> resultado = localService.buscarActivos();

        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByActivo(true);
    }

    @Test
    @DisplayName("eliminar() lanza excepcion cuando el local no existe")
    void eliminar_localNoExiste_lanzaExcepcion() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> localService.eliminar(9L));
        verify(repository, never()).deleteById(any());
    }
}
