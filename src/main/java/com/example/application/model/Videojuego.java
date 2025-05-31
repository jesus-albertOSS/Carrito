package com.example.application.model;

public class Videojuego {
    private String nombre;
    private double precio;

    public Videojuego(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
