package cl.duoc.pedidos_service.service;

import cl.duoc.pedidos_service.client.ClienteClient;
import cl.duoc.pedidos_service.client.ClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoConClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoDto;
import cl.duoc.pedidos_service.exception.ResourceNotFoundException;
import cl.duoc.pedidos_service.mapper.PedidoMapper;
import cl.duoc.pedidos_service.model.Pedido;
import cl.duoc.pedidos_service.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal; // IMPORTACIÓN AÑADIDA
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de la capa @Service (PedidoService).
 * Autor: Integrante 3.
 *
 * Ademas del repositorio y el mapper, aqui se simula (mock) el
 * ClienteClient (Feign): asi probamos la logica de PedidoService sin
 * necesitar que clientes-service este realmente corriendo.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService - capa Service")
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoMapper mapper;

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDto pedidoDto;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        // CORREGIDO: Se cambió .total(15990d) por BigDecimal.valueOf(15990.0)
        pedido = Pedido.builder()
                .id(1L)
                .clienteId(5L)
                .detalle("2 pizzas y bebida")
                .total(BigDecimal.valueOf(15990.0))
                .estado("PENDIENTE")
                .build();

        // CORREGIDO: Se cambió .total(15990d) por BigDecimal.valueOf(15990.0)
        pedidoDto = PedidoDto.builder()
                .id(1L)
                .clienteId(5L)
                .detalle("2 pizzas y bebida")
                .total(BigDecimal.valueOf(15990.0))
                .estado("PENDIENTE")
                .build();

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(5L);
        clienteDTO.setNombre("Ana Soto");
        clienteDTO.setEmail("ana@correo.com");
    }

    @Test
    @DisplayName("listar() retorna todos los pedidos mapeados")
    void listar_retornaListado() {
        when(repository.findAll()).thenReturn(List.of(pedido));
        when(mapper.toDto(pedido)).thenReturn(pedidoDto);

        List<PedidoDto> resultado = pedidoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("guardar() valida el cliente via Feign antes de crear el pedido")
    void guardar_validaClienteYCreaPedido() {
        when(clienteClient.obtenerCliente(5L)).thenReturn(clienteDTO);
        when(mapper.toModel(pedidoDto)).thenReturn(pedido);
        when(repository.save(pedido)).thenReturn(pedido);
        when(mapper.toDto(pedido)).thenReturn(pedidoDto);

        PedidoDto resultado = pedidoService.guardar(pedidoDto);

        assertEquals(5L, resultado.getClienteId());
        verify(clienteClient, times(1)).obtenerCliente(5L);
        verify(repository, times(1)).save(pedido);
    }

    @Test
    @DisplayName("buscarConCliente() combina el pedido con los datos del cliente")
    void buscarConCliente_combinaDatosCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));
        when(clienteClient.obtenerCliente(5L)).thenReturn(clienteDTO);

        PedidoConClienteDTO resultado = pedidoService.buscarConCliente(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Ana Soto", resultado.getCliente().getNombre());
    }

    @Test
    @DisplayName("buscarConCliente() lanza excepcion cuando el pedido no existe")
    void buscarConCliente_pedidoNoExiste_lanzaExcepcion() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.buscarConCliente(404L));
        verify(clienteClient, never()).obtenerCliente(any());
    }
}