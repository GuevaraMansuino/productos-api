package com.utn.productos_api.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Dejamos la clase vacía. SpringDoc escaneará las anotaciones
    // @Operation, @Tag, y @Schema de tus controladores y DTOs automáticamente.
    // El manejo del Enum Categoria se inferirá a partir del @PathVariable
    // en ProductoController.
}