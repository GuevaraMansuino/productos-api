package com.utn.productos_api.dto;

import com.utn.productos_api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta con la información completa del producto")
public class ProductoResponseDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop HP Pavilion")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Laptop con procesador Intel i5")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "899.99")
    private Double precio;

    @Schema(description = "Cantidad en stock", example = "50")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "ELECTRONICA")
    private Categoria categoria;
}