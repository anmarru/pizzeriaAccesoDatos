package controlador;

import modelo.pedido.Ingrediente;
import modelo.producto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.*;

class ControladorProductoTestJunit5 {
    private ControladorProducto controladorProducto = new ControladorProducto();

    private Producto pizza1, pizza2,pizza3,pasta1,pasta2, pasta3,bebida1, bebida2, bebida3;
   @BeforeEach
    void borrarCrear() throws SQLException {
        inicializarDatosPrueba();
    }

    private void inicializarDatosPrueba() throws SQLException {
        Ingrediente ingredientePeperoni = new Ingrediente("peperoni", List.of("sulfito", "lacteo"));
        Ingrediente ingredienteQueso = new Ingrediente("queso", List.of("lacteo"));
        Ingrediente ingredientePinia = new Ingrediente("piña", List.of());
        Ingrediente ingredientePollo = new Ingrediente("pollo", List.of("aves"));

        List<Ingrediente> ingredientesPasta1 = List.of(ingredienteQueso);
        List<Ingrediente> ingredientePasta3 = List.of(ingredientePollo);
        List<Ingrediente> ingredientesPasta4 = List.of(ingredienteQueso);

        List<Ingrediente> ingredientesPizza1 = List.of(ingredientePeperoni, ingredienteQueso);
        List<Ingrediente> ingredientesPizza2 = List.of(ingredientePeperoni, ingredienteQueso);
        List<Ingrediente> ingredientesPizza3 = List.of(ingredienteQueso, ingredientePinia);

        pasta1 = new Pasta("pasta carbonara", 10, ingredientesPasta1);
        pasta2 = new Pasta("pasta boloñesa", 8.5, ingredientesPasta4);
        pasta3 = new Pasta("pasta Fettuccini", 13, ingredientePasta3);

        pizza1 = new Pizza("peperoni", 7.30, Size.MEDIANA, ingredientesPizza2);
        pizza2 = new Pizza("hawai", 12, Size.GRANDE, ingredientesPizza3);
        pizza3 = new Pizza("margarita", 9.30, Size.PEQUEÑA, ingredientesPizza1);

        bebida1 = new Bebida("cocacola", 1.80, Size.GRANDE);
        bebida2 = new Bebida("cerveza", 3.50, Size.MEDIANA);
        bebida3 = new Bebida("agua", 0.90, Size.PEQUEÑA);
    }

    /*@Disabled
    @Test
    void borrarYcrearTablas() throws SQLException {
        crearTablas();
        controladorProducto.borrarYcrearTablas();
    }
    @Disabled
    @Test
    void crearTablas() throws SQLException {
        controladorProducto.crearTablas();
    }*/


    @Test
    void guardarProducto() throws SQLException {
        controladorProducto.guardarProducto(pasta1);
        controladorProducto.guardarProducto(pasta2);
        controladorProducto.guardarProducto(pasta3);
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.guardarProducto(pizza2);
        controladorProducto.guardarProducto(pizza3);
        controladorProducto.guardarProducto(bebida1);
        controladorProducto.guardarProducto(bebida2);
        controladorProducto.guardarProducto(bebida3);

        Producto productoGuardado = controladorProducto.obtenerProductoPorId(pasta1.getId());
        assertNotNull(productoGuardado);//verifico si el producto se guardo
        assertEquals("pasta carbonara", productoGuardado.getNombre());
    }



    @Test
    void actualizarProducto() throws SQLException {
        controladorProducto.guardarProducto(pasta1);
        pasta1=controladorProducto.obtenerProductoPorId(1);
        pasta1.setNombre("Pasta cambiada");
        controladorProducto.actualizarProducto(pasta1);

        Producto productoActualizado = controladorProducto.obtenerProductoPorId(pasta1.getId());
        assertEquals("Pasta cambiada", productoActualizado.getNombre());
    }

    @Test
    void borrarProductoId() throws SQLException {
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.borrarProductoId(1);
        List<Producto> productos= controladorProducto.findAll();
        assertEquals(0, productos.size());
    }

    @Test
    void findAll() throws SQLException {
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.guardarProducto(pizza2);
        controladorProducto.guardarProducto(pizza3);
        List<Producto> productos= controladorProducto.findAll();
        assertEquals(3, productos.size());
        productos.forEach(System.out::println);
    }

    @Test
    void obtenerIngredientePorProducto() throws SQLException {
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.guardarProducto(pizza2);
        controladorProducto.guardarProducto(pizza3);
        controladorProducto.guardarProducto(pasta1);
        controladorProducto.guardarProducto(pasta2);
        int idproducto=4;
        Producto producto=controladorProducto.obtenerProductoPorId(idproducto);
        List<Ingrediente> ingredientes = ((Pasta)producto).getIngredientes();
        assertNotNull(ingredientes);
        assertTrue(ingredientes.size() > 0);
        ingredientes.forEach(System.out::println);
    }

    @Test
    void obtenerAlergenosPorIngrediente() throws SQLException {
        controladorProducto.guardarProducto(pizza2);
        Producto producto= controladorProducto.obtenerProductoPorId(1);
        List<Ingrediente> ingredientes = ((Pizza)producto).getIngredientes();
        List<String> alergenos=new ArrayList<>();
        for(Ingrediente ingrediente: ingredientes){
            alergenos.addAll(ingrediente.getAlergenos());
        }
        alergenos = new HashSet<>(alergenos).stream().toList();//para que no se repitan
        if (alergenos.isEmpty()) {
            System.out.println("Estos ingredientes no tiene alérgenos.");
        } else {
            alergenos.forEach(System.out::println);  // Imprime los alérgenos si existen
        }
        assertTrue("La lista de alérgenos debe estar vacía o contener elementos.", alergenos.size() > 0);
    }

    @Test
    void obtenerProductoPorId() throws SQLException {
        controladorProducto.guardarProducto(pasta1);
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.guardarProducto(pizza2);
        controladorProducto.guardarProducto(bebida1);
        controladorProducto.guardarProducto(bebida3);
        Producto producto= controladorProducto.obtenerProductoPorId(2);
        System.out.println(producto);
        assertEquals(2, producto.getId());
    }
}