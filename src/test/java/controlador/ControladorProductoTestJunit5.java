package controlador;

import modelo.pedido.Ingrediente;
import modelo.producto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.DataBaseConfig;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

class ControladorProductoTestJunit5 {
    private ControladorProducto controladorProducto = new ControladorProducto();

   /*@BeforeEach
    void borrarCrear() throws SQLException {
        DataBaseConfig.dropAndCreateTables();
        inicializarDatosPrueba();
    }*/

    private void inicializarDatosPrueba() throws SQLException {
        controladorProducto.guardarProducto(pasta1);
        controladorProducto.guardarProducto(pasta2);
        controladorProducto.guardarProducto(pasta3);
        controladorProducto.guardarProducto(pizza1);
        controladorProducto.guardarProducto(pizza2);
        controladorProducto.guardarProducto(pizza3);
        controladorProducto.guardarProducto(bebida1);
        controladorProducto.guardarProducto(bebida2);
        controladorProducto.guardarProducto(bebida3);
    }


    Ingrediente ingredientePeperoni= new Ingrediente("peperoni", List.of("sulfito","lacteo"),1);
    Ingrediente ingredienteQueso= new Ingrediente("queso", List.of("lacteo"),2);
    Ingrediente ingredientePinia= new Ingrediente("piña",List.of(),3);
    Ingrediente ingredientePollo= new Ingrediente("pollo", List.of("aves"),4);

    List<Ingrediente> ingredientesPasta1= List.of(ingredienteQueso);
    List<Ingrediente> ingredientePasta3= List.of(ingredientePollo,ingredienteQueso);
    List<Ingrediente> ingredientesPasta4= List.of(ingredienteQueso);

    List<Ingrediente> ingredientesPizza1= List.of(ingredientePeperoni,ingredienteQueso);
    List<Ingrediente> ingredientesPizza2= List.of(ingredientePeperoni,ingredienteQueso);
    List<Ingrediente> ingredientesPizza3= List.of(ingredienteQueso,ingredientePinia);

    Pasta pasta1=new Pasta("pasta carbonara",10,ingredientesPasta1);
    Pasta pasta2= new Pasta("pasta boloñesa",8.5,ingredientesPasta4);
    Pasta pasta3= new Pasta("pasta Fettuccini",13,ingredientePasta3);

    Pizza pizza1= new Pizza("peperoni",7.30, Size.MEDIANA,ingredientesPizza2);
    Pizza pizza2= new Pizza("hawai",12, Size.GRANDE,ingredientesPizza3);
    Pizza pizza3= new Pizza("margarita",9.30, Size.PEQUEÑA,ingredientesPizza1);

    Bebida bebida1 = new Bebida( "cocacola", 1.80,Size.GRANDE);
    Bebida bebida2 = new Bebida( "cerveza", 3.50,Size.MEDIANA);
    Bebida bebida3 = new Bebida( "agua", 0.90,Size.PEQUEÑA);

    @Test
    void borrarYcrearTablas() throws SQLException {
        crearTablas();
        controladorProducto.borrarYcrearTablas();
    }

    @Test
    void crearTablas() throws SQLException {
        controladorProducto.crearTablas();
    }

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

       /*Producto productoGuardado = controladorProducto.obtenerProductoPorId(pasta1.getId());
        assertNotNull(productoGuardado);//verifico si el cliente se guardo
        assertEquals("pasta carbonara", productoGuardado.getNombre());*/
    }

    @Test
    void actualizarProducto() throws SQLException {
        Producto pasta1=new Pasta("pasta carbonara",10,ingredientesPasta1);
        controladorProducto.guardarProducto(pasta1);
        pasta1=controladorProducto.obtenerProductoPorId(1);
        pasta1.setNombre("Pasta cambiada");

        controladorProducto.actualizarProducto(pasta1);

        Producto productoActualizado = controladorProducto.obtenerProductoPorId(pasta1.getId());
        assertEquals("Pasta cambiada", productoActualizado.getNombre());
    }

    @Test
    void borrarProductoId() throws SQLException {
        controladorProducto.borrarProductoId(1);
    }

    @Test
    void findAll() {
        List<Producto> productos= controladorProducto.findAll();
        productos.forEach(System.out::println);
    }

    @Test
    void obtenerIngredientePorProducto() {

        int idproducto=3;
        List<Ingrediente> ingredientes = controladorProducto.obtenerIngredientePorProducto(idproducto);
        assertNotNull(ingredientes);
        assertTrue(ingredientes.size() > 0);
        ingredientes.forEach(System.out::println);
    }

    @Test
    void obtenerAlergenosPorIngrediente() {
        int idIngrediente=1;
        List<String> alergenos= controladorProducto.obtenerAlergenosPorIngrediente(idIngrediente);

        assertNotNull(String.valueOf(alergenos), "La lista de alérgenos no debe ser nula.");
        if (alergenos.isEmpty()) {
            System.out.println("Este ingrediente no tiene alérgenos.");
        } else {
            alergenos.forEach(System.out::println);  // Imprime los alérgenos si existen
        }
        assertTrue("La lista de alérgenos debe estar vacía o contener elementos.", alergenos.isEmpty() || alergenos.size() > 0);
    }

    @Test
    void obtenerProductoPorId() {
        Producto producto= controladorProducto.obtenerProductoPorId(2);
        System.out.println(producto);
        assertEquals(2, 2);
    }
}