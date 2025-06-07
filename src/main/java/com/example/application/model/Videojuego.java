package com.example.application.model;

public class Videojuego extends Producto {

    private String imagenUrl;

    public Videojuego(String nombre, double precio, String imagenUrl) {
        super(nombre, precio);
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    @Override
    public String getCategoria() {
        return "Videojuego";
    }
}