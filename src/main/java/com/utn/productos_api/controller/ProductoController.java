package com.utn.productos_api.controller;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para la gestión de productos del e-commerce")
public class ProductoController {

    private final ProductoService productoService;

    // Inyección por constructor
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos - Listar todos
    @GetMapping
    @Operation(summary = "Listar todos los productos",
            description = "Obtiene una lista completa de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                    // CORRECCIÓN: Para indicar que retorna una LISTA de ProductoResponseDTO,
                    // se debe usar ProductoResponseDTO[].class en el implementation.
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO[].class)))
    })
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    // GET /api/productos/{id} - Obtener por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID",
            description = "Obtiene los detalles de un producto específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del producto a buscar", example = "1")
            @PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    // GET /api/productos/categoria/{categoria} - Filtrar por categoría
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar productos por categoría",
            description = "Obtiene todos los productos que pertenecen a una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos filtrada por categoría",
                    // CORRECCIÓN: Esto también retorna una lista, debe ser ProductoResponseDTO[].class
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO[].class)))
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(
            @Parameter(description = "Categoría a filtrar", example = "ELECTRONICA")
            @PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    // POST /api/productos - Crear nuevo producto
    @PostMapping
    @Operation(summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el sistema con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductoResponseDTO> crearProducto(
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO nuevoProducto = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // PUT /api/productos/{id} - Actualizar producto completo
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto completo",
            description = "Actualiza todos los campos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    // PATCH /api/productos/{id}/stock - Actualizar stock
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Actualizar stock del producto",
            description = "Actualiza únicamente la cantidad de stock de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Stock inválido")
    })
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO stockDTO) {
        ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, stockDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    // DELETE /api/productos/{id} - Eliminar producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto",
            description = "Elimina un producto del sistema de forma permanente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}