package com.utn.productos_api.service;

import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.exception.ProductoNotFoundException;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Crear un nuevo producto
    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = convertirDTOaEntidad(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return convertirEntidadaResponseDTO(productoGuardado);
    }

    // Obtener todos los productos
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirEntidadaResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener producto por ID
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
        return convertirEntidadaResponseDTO(producto);
    }

    // Obtener productos por categoría
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::convertirEntidadaResponseDTO)
                .collect(Collectors.toList());
    }

    // Actualizar producto completo
    public ProductoResponseDTO actualizarProducto(Long id, com.utn.productos_api.dto.@Valid ProductoDTO productoDTO) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());
        productoExistente.setCategoria(productoDTO.getCategoria());

        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertirEntidadaResponseDTO(productoActualizado);
    }

    // Actualizar solo el stock
    public ProductoResponseDTO actualizarStock(Long id, com.utn.productos_api.dto.@Valid ActualizarStockDTO stockDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));

        producto.setStock(stockDTO.getStock());
        Producto productoActualizado = productoRepository.save(producto);
        return convertirEntidadaResponseDTO(productoActualizado);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    // Métodos auxiliares de conversión
    private Producto convertirDTOaEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }

    private ProductoResponseDTO convertirEntidadaResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        return dto;
    }

}