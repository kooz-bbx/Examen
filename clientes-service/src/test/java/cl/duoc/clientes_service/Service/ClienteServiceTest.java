package cl.duoc.clientes_service.Service;

import cl.duoc.clientes_service.Dto.ClienteDTO;
import cl.duoc.clientes_service.Exception.ResourceNotFoundException;
import cl.duoc.clientes_service.Mapper.ClienteMapper;
import cl.duoc.clientes_service.Model.Cliente;
import cl.duoc.clientes_service.Repository.ClienteRepository;
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
 * Pruebas unitarias de la capa @Service (ClienteService).
 * Autor: Integrante 1.
 *
 * Se mockean ClienteRepository y ClienteMapper: la idea es probar SOLO
 * la logica de ClienteService, sin levantar base de datos.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService - capa Service")
class ClienteServiceTest {

    @Mock
    private ClienteRepository repo;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L).nombre("Ana Soto").email("ana@correo.com")
                .telefono("+56911112222").ciudad("Santiago")
                .build();

        clienteDTO = ClienteDTO.builder()
                .id(1L).nombre("Ana Soto").email("ana@correo.com")
                .telefono("+56911112222").ciudad("Santiago")
                .build();
    }

    @Test
    @DisplayName("listar() retorna todos los clientes mapeados")
    void listar_retornaListado() {
        when(repo.findAll()).thenReturn(List.of(cliente));
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        List<ClienteDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Ana Soto", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscar() retorna el cliente cuando existe")
    void buscar_clienteExiste_retornaDTO() {
        when(repo.findById(1L)).thenReturn(Optional.of(cliente));
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = service.buscar(1L);

        assertEquals("ana@correo.com", resultado.getEmail());
    }

    @Test
    @DisplayName("buscar() lanza excepcion cuando el cliente no existe")
    void buscar_clienteNoExiste_lanzaExcepcion() {
        when(repo.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscar(404L));
    }

    @Test
    @DisplayName("guardar() persiste y retorna el cliente creado")
    void guardar_creaClienteCorrectamente() {
        when(mapper.toEntity(clienteDTO)).thenReturn(cliente);
        when(repo.save(cliente)).thenReturn(cliente);
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = service.guardar(clienteDTO);

        assertEquals("Ana Soto", resultado.getNombre());
        verify(repo, times(1)).save(cliente);
    }

    @Test
    @DisplayName("buscarPorCiudad() filtra correctamente por ciudad")
    void buscarPorCiudad_retornaClientesDeLaCiudad() {
        when(repo.findByCiudad("Santiago")).thenReturn(List.of(cliente));
        when(mapper.toDTO(cliente)).thenReturn(clienteDTO);

        List<ClienteDTO> resultado = service.buscarPorCiudad("Santiago");

        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByCiudad("Santiago");
    }

    @Test
    @DisplayName("eliminar() lanza excepcion cuando el cliente no existe")
    void eliminar_clienteNoExiste_lanzaExcepcion() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.eliminar(99L));
        verify(repo, never()).deleteById(any());
    }
}
