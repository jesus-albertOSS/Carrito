package com.example.application.views.myview;

import com.example.application.model.Videojuego;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "", layout = MainLayout.class)
public class MainView extends HorizontalLayout {

    private final List<Videojuego> videojuegos = new ArrayList<>();
    private final List<Videojuego> carrito = new ArrayList<>();
    private final VerticalLayout carritoLayout = new VerticalLayout();
    private final Span totalSpan = new Span();
    private final Select<String> metodoPagoSelect = new Select<>();

    public MainView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Lista de videojuegos
        VerticalLayout productosLayout = new VerticalLayout();
        productosLayout.setWidth("65%");
        productosLayout.setPadding(true);
        productosLayout.setSpacing(true);

        productosLayout.add(new H1("🕹️ Tienda de Videojuegos"));
        
        videojuegos.add(new Videojuego("Minecraft", 25.99, "/images/MINECRAFT-PS4-DIGITAL.jpg"));
videojuegos.add(new Videojuego("FIFA 24", 59.99, "/images/fc244444-f3b53f3a4871b7422417045720228928-640-0.jpg"));
videojuegos.add(new Videojuego("Elden Ring", 49.99, "/images/81mtrRvlFqL._AC_UF350,350_QL50_.jpg"));
videojuegos.add(new Videojuego("Cyberpunk 2077", 29.99, "/images/81YptknEr3L._AC_UF1000,1000_QL80_.jpg"));

for (Videojuego v : videojuegos) {
    HorizontalLayout fila = new HorizontalLayout();
    fila.setAlignItems(Alignment.CENTER);
    fila.setWidthFull();
    fila.setSpacing(true);

    Image imagen = new Image(v.getImagenUrl(), v.getNombre());
    imagen.setHeight("80px");
    imagen.getStyle().set("border-radius", "8px");
    imagen.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.2)");

    Span nombre = new Span(v.getNombre() + " - $" + v.getPrecio());
    nombre.getStyle().set("flex-grow", "1");

    Button agregar = new Button("Agregar al carrito", e -> {
        carrito.add(v);
        actualizarCarrito();
    });

    fila.add(imagen, nombre, agregar);
    productosLayout.add(fila);
}

        // Carrito de compras
        VerticalLayout panelCarrito = new VerticalLayout();
        panelCarrito.setWidth("35%");
        panelCarrito.getStyle().set("border", "2px solid #ddd");
        panelCarrito.getStyle().set("border-radius", "10px");
        panelCarrito.getStyle().set("padding", "15px");
        panelCarrito.getStyle().set("background-color", "#f9f9f9");
        panelCarrito.getStyle().set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)");

        H2 tituloCarrito = new H2("🛒 Carrito de Compras");
        tituloCarrito.getStyle().set("margin-top", "0");

        metodoPagoSelect.setLabel("Método de Pago");
        metodoPagoSelect.setItems("Tarjeta de crédito", "PayPal", "Transferencia bancaria");

        Button finalizarCompra = new Button("Finalizar compra", e -> mostrarFactura());

        panelCarrito.add(tituloCarrito, carritoLayout, totalSpan, metodoPagoSelect, finalizarCompra);

        add(productosLayout, panelCarrito);
        actualizarCarrito();
    }

    private void actualizarCarrito() {
        carritoLayout.removeAll();

        if (carrito.isEmpty()) {
            carritoLayout.add(new Span("El carrito está vacío."));
            totalSpan.setText("");
        } else {
            double total = 0;
            for (Videojuego v : new ArrayList<>(carrito)) {
                HorizontalLayout item = new HorizontalLayout();
                item.setWidthFull();
                item.setAlignItems(Alignment.CENTER);

                Span nombre = new Span(v.getNombre() + " - $" + v.getPrecio());
                nombre.getStyle().set("flex-grow", "1");

                Button eliminar = new Button("❌", e -> {
                    carrito.remove(v);
                    actualizarCarrito();
                });
                eliminar.getStyle().set("color", "red");

                item.add(nombre, eliminar);
                carritoLayout.add(item);

                total += v.getPrecio();
            }
            carritoLayout.add(new Hr());
            totalSpan.setText("Total: $" + String.format("%.2f", total));
        }
    }

    private void mostrarFactura() {
        if (carrito.isEmpty()) {
            Dialog aviso = new Dialog();
            aviso.add(new Span("Tu carrito está vacío."));
            aviso.open();
            return;
        }

        if (metodoPagoSelect.isEmpty()) {
            Dialog aviso = new Dialog();
            aviso.add(new Span("Selecciona un método de pago."));
            aviso.open();
            return;
        }

        double total = carrito.stream().mapToDouble(Videojuego::getPrecio).sum();

        Dialog factura = new Dialog();
        VerticalLayout contenido = new VerticalLayout();
        contenido.setPadding(true);
        contenido.setSpacing(false);

        contenido.add(new H3("🧾 Factura"));

        for (Videojuego v : carrito) {
            contenido.add(new Span(v.getNombre() + " - $" + v.getPrecio()));
        }

        contenido.add(new Hr());
        contenido.add(new Span("Método de pago: " + metodoPagoSelect.getValue()));
        contenido.add(new Span("Total: $" + String.format("%.2f", total)));

        Button cerrar = new Button("Cerrar", e -> factura.close());
        contenido.add(cerrar);

        factura.add(contenido);
        factura.open();
    }
    
}
