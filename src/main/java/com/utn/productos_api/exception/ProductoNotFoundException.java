package com.utn.productos_api.exception;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }

    public ProductoNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}