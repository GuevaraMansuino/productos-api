package com.utn.productos_api.config;

import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            // Productos de ELECTRONICA
            repository.save(new Producto(null, "Laptop HP Pavilion",
                    "Laptop con procesador Intel i5, 8GB RAM, 256GB SSD",
                    899.99, 50, Categoria.ELECTRONICA));

            repository.save(new Producto(null, "Mouse Logitech MX Master",
                    "Mouse inalámbrico ergonómico de alta precisión",
                    79.99, 120, Categoria.ELECTRONICA));

            repository.save(new Producto(null, "Teclado Mecánico Razer",
                    "Teclado mecánico RGB para gaming",
                    149.99, 80, Categoria.ELECTRONICA));

            // Productos de ROPA
            repository.save(new Producto(null, "Camiseta Nike Dri-Fit",
                    "Camiseta deportiva de secado rápido",
                    29.99, 200, Categoria.ROPA));

            repository.save(new Producto(null, "Pantalón Adidas",
                    "Pantalón deportivo de algodón",
                    49.99, 150, Categoria.ROPA));

            // Productos de ALIMENTOS
            repository.save(new Producto(null, "Café Colombiano Premium",
                    "Café 100% arábica de origen colombiano, 500g",
                    15.99, 300, Categoria.ALIMENTOS));

            repository.save(new Producto(null, "Aceite de Oliva Extra Virgen",
                    "Aceite de oliva prensado en frío, 1L",
                    12.99, 180, Categoria.ALIMENTOS));

            // Productos de HOGAR
            repository.save(new Producto(null, "Juego de Sábanas Queen",
                    "Sábanas de algodón egipcio 300 hilos",
                    89.99, 60, Categoria.HOGAR));

            repository.save(new Producto(null, "Cafetera Nespresso",
                    "Cafetera de cápsulas automática",
                    199.99, 40, Categoria.HOGAR));

            // Productos de DEPORTES
            repository.save(new Producto(null, "Pelota de Fútbol Adidas",
                    "Pelota oficial de fútbol profesional",
                    45.99, 100, Categoria.DEPORTES));

            repository.save(new Producto(null, "Mancuernas Ajustables 20kg",
                    "Set de mancuernas con peso ajustable",
                    129.99, 75, Categoria.DEPORTES));

            System.out.println("✅ Base de datos inicializada con productos de ejemplo");
        };
    }
}