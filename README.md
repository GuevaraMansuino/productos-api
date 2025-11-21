# 🛒 API REST - Gestión de Productos

## 📝 Descripción del Proyecto

API REST completa y profesional para la gestión de productos de un sistema de e-commerce. Implementa las mejores prácticas de desarrollo con Spring Boot, incluyendo arquitectura en capas, validaciones, manejo centralizado de errores, persistencia con JPA y documentación interactiva con Swagger.

Este proyecto fue desarrollado como trabajo práctico para la materia Programación III de la Tecnicatura Universitaria en Programación.

---

## ⚙️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
    - Spring Web (API REST)
    - Spring Data JPA (Persistencia)
    - Spring Validation (Validación de datos)
- **H2 Database** (Base de datos en memoria)
- **Lombok** (Reducción de código boilerplate)
- **Springdoc OpenAPI 2.3.0** (Swagger UI)
- **Maven** (Gestión de dependencias)

---

## 🏗️ Arquitectura del Proyecto

```
com.utn.productos
├── model/
│   ├── Categoria.java (enum)
│   └── Producto.java (@Entity)
├── dto/
│   ├── ProductoDTO.java (crear/actualizar)
│   ├── ProductoResponseDTO.java (respuestas)
│   └── ActualizarStockDTO.java (actualizar stock)
├── repository/
│   └── ProductoRepository.java (JpaRepository)
├── service/
│   └── ProductoService.java (@Service)
├── controller/
│   └── ProductoController.java (@RestController)
├── exception/
│   ├── ProductoNotFoundException.java
│   ├── StockInsuficienteException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java (@ControllerAdvice)
└── ProductosApiApplication.java (main)
```

### Patrón de Arquitectura en Capas

- **Capa de Presentación**: `ProductoController` - Maneja las peticiones HTTP
- **Capa de Negocio**: `ProductoService` - Contiene la lógica de negocio
- **Capa de Persistencia**: `ProductoRepository` - Acceso a datos
- **Capa de Modelo**: Entidades y DTOs
- **Manejo de Errores**: Centralizado con `@ControllerAdvice`

---

## 🚀 Instrucciones para Clonar y Ejecutar

### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/productos-api-springboot.git
cd productos-api-springboot
```

### 2️⃣ Compilar el Proyecto

```bash
mvn clean install
```

### 3️⃣ Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O desde tu IDE favorito (IntelliJ IDEA, Eclipse, VS Code):
- Ejecuta la clase `ProductosApiApplication.java`

### 4️⃣ Acceder a la Aplicación

- **API Base URL**: `http://localhost:8080/api/productos`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Consola H2**: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:productosdb`
    - Username: `sa`
    - Password: _(dejar vacío)_

---

## 🌐 Endpoints de la API

| Método | Endpoint | Descripción | Código de Estado |
|--------|----------|-------------|------------------|
| **GET** | `/api/productos` | Listar todos los productos | 200 OK |
| **GET** | `/api/productos/{id}` | Obtener producto por ID | 200 OK / 404 Not Found |
| **GET** | `/api/productos/categoria/{categoria}` | Filtrar por categoría | 200 OK |
| **POST** | `/api/productos` | Crear nuevo producto | 201 Created / 400 Bad Request |
| **PUT** | `/api/productos/{id}` | Actualizar producto completo | 200 OK / 404 Not Found |
| **PATCH** | `/api/productos/{id}/stock` | Actualizar solo el stock | 200 OK / 404 Not Found |
| **DELETE** | `/api/productos/{id}` | Eliminar producto | 204 No Content / 404 Not Found |

---

## 📋 Ejemplos de Uso

### Crear un Producto (POST)

**Request:**
```json
POST /api/productos
Content-Type: application/json

{
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop con procesador Intel i5, 8GB RAM, 256GB SSD",
  "precio": 899.99,
  "stock": 50,
  "categoria": "ELECTRONICA"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop con procesador Intel i5, 8GB RAM, 256GB SSD",
  "precio": 899.99,
  "stock": 50,
  "categoria": "ELECTRONICA"
}
```

### Obtener Todos los Productos (GET)

**Request:**
```
GET /api/productos
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombre": "Laptop HP Pavilion",
    "descripcion": "Laptop con procesador Intel i5, 8GB RAM, 256GB SSD",
    "precio": 899.99,
    "stock": 50,
    "categoria": "ELECTRONICA"
  },
  {
    "id": 2,
    "nombre": "Camiseta Nike",
    "descripcion": "Camiseta deportiva de algodón",
    "precio": 29.99,
    "stock": 100,
    "categoria": "ROPA"
  }
]
```

### Actualizar Stock (PATCH)

**Request:**
```json
PATCH /api/productos/1/stock
Content-Type: application/json

{
  "stock": 75
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop con procesador Intel i5, 8GB RAM, 256GB SSD",
  "precio": 899.99,
  "stock": 75,
  "categoria": "ELECTRONICA"
}
```

### Error - Producto No Encontrado (404)

**Request:**
```
GET /api/productos/999
```

**Response:** `404 Not Found`
```json
{
  "timestamp": "2024-11-18T10:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "Producto no encontrado con ID: 999",
  "path": "/api/productos/999"
}
```

### Error - Validación (400)

**Request:**
```json
POST /api/productos
Content-Type: application/json

{
  "nombre": "AB",
  "precio": -10,
  "stock": -5
}
```

**Response:** `400 Bad Request`
```json
{
  "timestamp": "2024-11-18T10:35:00",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Error de validación en los datos de entrada",
  "path": "/api/productos",
  "detalles": [
    "nombre: El nombre debe tener entre 3 y 100 caracteres",
    "precio: El precio debe ser mayor a 0",
    "stock: El stock no puede ser negativo",
    "categoria: La categoría es obligatoria"
  ]
}
```

---

## 📸 Capturas de Pantalla

### 1. Swagger UI - Documentación Completa
_(Aquí va tu captura de la interfaz de Swagger mostrando todos los endpoints)_

### 2. POST - Crear Producto Exitoso (201 Created)
_(Captura mostrando la creación exitosa de un producto)_

### 3. GET - Listar Productos (200 OK)
_(Captura mostrando la lista de productos)_

### 4. GET - Producto No Encontrado (404)
_(Captura mostrando el error 404 con el mensaje apropiado)_

### 5. POST - Error de Validación (400 Bad Request)
_(Captura mostrando errores de validación detallados)_

### 6. Consola H2 - Datos Persistidos
_(Captura de la tabla productos en la consola H2 con datos)_
![img.png](img.png)

---

## 🔧 Validaciones Implementadas

### ProductoDTO
- **Nombre**: Obligatorio, no vacío, entre 3 y 100 caracteres
- **Descripción**: Máximo 500 caracteres
- **Precio**: Obligatorio, mínimo 0.01
- **Stock**: Obligatorio, mínimo 0
- **Categoría**: Obligatoria

### ActualizarStockDTO
- **Stock**: Obligatorio, mínimo 0

---

## 🎯 Funcionalidades Implementadas

✅ CRUD completo de productos  
✅ Validación de datos con Bean Validation  
✅ DTOs para desacoplar capas  
✅ Manejo centralizado de excepciones  
✅ Respuestas HTTP apropiadas (200, 201, 204, 400, 404, 500)  
✅ Persistencia con Spring Data JPA y H2  
✅ Queries personalizadas (buscar por categoría)  
✅ Documentación interactiva con Swagger  
✅ Arquitectura en capas profesional  
✅ Códigos de estado HTTP semánticamente correctos

---

## 🧪 Cómo Probar la API

### Opción 1: Swagger UI (Recomendado)
1. Ejecuta la aplicación
2. Abre `http://localhost:8080/swagger-ui.html`
3. Prueba cada endpoint directamente desde la interfaz

### Opción 2: Postman o Insomnia
Importa la colección de endpoints o usa las URLs directamente.

### Opción 3: cURL
```bash
# Crear producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Producto Test","precio":99.99,"stock":10,"categoria":"ELECTRONICA"}'

# Listar productos
curl http://localhost:8080/api/productos

# Obtener por ID
curl http://localhost:8080/api/productos/1
```

---

## 📊 Categorías Disponibles

- `ELECTRONICA` - Productos electrónicos
- `ROPA` - Prendas de vestir
- `ALIMENTOS` - Productos alimenticios
- `HOGAR` - Artículos para el hogar
- `DEPORTES` - Equipamiento deportivo

---

## 🔒 Manejo de Errores

La API implementa un manejo centralizado de errores con respuestas consistentes:

- **404 Not Found**: Recurso no encontrado
- **400 Bad Request**: Datos inválidos o error de validación
- **500 Internal Server Error**: Error interno del servidor

Todas las respuestas de error incluyen:
- Timestamp
- Código de estado
- Mensaje descriptivo
- Ruta de la petición
- Detalles adicionales (en caso de validación)

---

## 💭 Conclusiones Personales

A través de este trabajo práctico he logrado:

1. **Diseñar APIs RESTful**: Aplicar correctamente los principios REST y los métodos HTTP según su semántica (GET, POST, PUT, PATCH, DELETE).

2. **Arquitectura en Capas**: Implementar una separación clara de responsabilidades entre controladores, servicios y repositorios, facilitando el mantenimiento y testing.

3. **DTOs y Desacoplamiento**: Usar Data Transfer Objects para separar la capa de presentación del modelo de dominio, mejorando la seguridad y flexibilidad.

4. **Validación Declarativa**: Aplicar Bean Validation para validar datos de entrada de forma declarativa y consistente.

5. **Manejo de Errores Profesional**: Implementar un sistema centralizado de manejo de excepciones que proporciona respuestas coherentes y significativas.

6. **Persistencia con JPA**: Trabajar con Spring Data JPA para abstraer la capa de persistencia y simplificar operaciones CRUD.

7. **Documentación Automática**: Generar documentación interactiva con Swagger/OpenAPI que facilita el consumo de la API por otros desarrolladores.

8. **Buenas Prácticas**: Aplicar principios SOLID, inyección de dependencias, códigos de estado HTTP apropiados y estructura de proyecto profesional.

Este proyecto me ha dado las bases para desarrollar APIs REST escalables, mantenibles y profesionales, habilidades esenciales en el desarrollo de software moderno.

---

## 👤 Autor

**Nombre**: [Geronimo Guevara Mansuino]  
**Legajo**: [52661]  
**Materia**: Programación III  
**Institución**: Tecnicatura Universitaria en Programación - UTN

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos para la UTN.

---
