package cl.duoc.productos_service.Service;

import cl.duoc.productos_service.Dto.ProductoDto;
import cl.duoc.productos_service.Exception.ResourceNotFoundException;
import cl.duoc.productos_service.Mapper.ProductoMapper;
import cl.duoc.productos_service.Model.Producto;
import cl.duoc.productos_service.Repository.ProductoRepository;
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
 * Pruebas unitarias de la capa @Service (ProductoService).
 * Autor: Integrante 3.
 *
 * ProductoRepository y ProductoMapper se simulan con Mockito para
 * probar unicamente la logica de ProductoService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductoService - capa Service")
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        // CORREGIDO: Se cambió .precio(8990d) por BigDecimal.valueOf(8990.0)
        producto = Producto.builder()
                .id(1L).nombre("Pizza Margarita").precio(BigDecimal.valueOf(8990.0))
                .stock(20).categoria("Pizzas").disponible(true)
                .build();

        // CORREGIDO: Se cambió .precio(8990d) por BigDecimal.valueOf(8990.0)
        productoDto = ProductoDto.builder()
                .id(1L).nombre("Pizza Margarita").precio(BigDecimal.valueOf(8990.0))
                .stock(20).categoria("Pizzas").disponible(true)
                .build();
    }

    @Test
    @DisplayName("obtenerTodos() retorna todos los productos mapeados")
    void obtenerTodos_retornaListado() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        List<ProductoDto> resultado = productoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Pizza Margarita", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("buscarPorId() retorna el producto cuando existe")
    void buscarPorId_productoExiste_retornaDTO() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        ProductoDto resultado = productoService.buscarPorId(1L);

        // CORREGIDO: Se ajustó el assertEquals para evaluar BigDecimal correctamente
        assertEquals(BigDecimal.valueOf(8990.0), resultado.getPrecio());
    }

    @Test
    @DisplayName("buscarPorId() lanza excepcion cuando el producto no existe")
    void buscarPorId_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.buscarPorId(999L));
    }

    @Test
    @DisplayName("guardar() persiste y retorna el producto creado")
    void guardar_creaProductoCorrectamente() {
        when(productoMapper.toEntity(productoDto)).thenReturn(producto);
        when(productoRepository.save(producto)).thenReturn(producto);
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        ProductoDto resultado = productoService.guardar(productoDto);

        assertEquals("Pizzas", resultado.getCategoria());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    @DisplayName("buscarDisponibles() retorna solo productos disponibles")
    void buscarDisponibles_retornaSoloDisponibles() {
        when(productoRepository.findByDisponible(true)).thenReturn(List.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);

        List<ProductoDto> resultado = productoService.buscarDisponibles();

        assertEquals(1, resultado.size());
        verify(productoRepository, times(1)).findByDisponible(true);
    }

    @Test
    @DisplayName("eliminar() lanza excepcion cuando el producto no existe")
    void eliminar_productoNoExiste_lanzaExcepcion() {
        when(productoRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.eliminar(50L));
        verify(productoRepository, never()).deleteById(any());
    }
}