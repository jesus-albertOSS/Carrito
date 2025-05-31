package com.example.application.views.myview;

import com.example.application.model.Videojuego;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private List<Videojuego> videojuegos = new ArrayList<>();
    private List<Videojuego> carrito = new ArrayList<>();
    private VerticalLayout carritoLayout = new VerticalLayout();

    public MainView() {
        setPadding(true);
        setSpacing(true);

        add(new H1("🕹️ Tienda de Videojuegos"));

        // Agregamos productos a la lista
        videojuegos.add(new Videojuego("Minecraft", 25.99));
        videojuegos.add(new Videojuego("FIFA 24", 59.99));
        videojuegos.add(new Videojuego("Elden Ring", 49.99));
        videojuegos.add(new Videojuego("Cyberpunk 2077", 29.99));

        // Mostrar productos
        for (Videojuego v : videojuegos) {
            HorizontalLayout fila = new HorizontalLayout();
            fila.setAlignItems(Alignment.CENTER);

            Span nombre = new Span(v.getNombre() + " - $" + v.getPrecio());

            Button agregar = new Button("Agregar al carrito", e -> {
                carrito.add(v);
                actualizarCarrito();
            });

            fila.add(nombre, agregar);
            add(fila);
        }

        // Mostrar el carrito
        add(new H2("🛒 Carrito de Compras"));
        add(carritoLayout);
        actualizarCarrito();
    }

    private void actualizarCarrito() {
        carritoLayout.removeAll();

        if (carrito.isEmpty()) {
            carritoLayout.add(new Span("El carrito está vacío."));
        } else {
            double total = 0;
            for (Videojuego v : carrito) {
                carritoLayout.add(new Span(v.getNombre() + " - $" + v.getPrecio()));
                total += v.getPrecio();
            }
            carritoLayout.add(new Hr());
            carritoLayout.add(new Span("Total: $" + total));
        }
    }
}
