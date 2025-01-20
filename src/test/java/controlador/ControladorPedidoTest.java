package controlador;

import controlador.dao.PedidoDao;
import controlador.dao.implement.JdbcPedidoDao;
import modelo.cliente.Cliente;
import modelo.pedido.*;
import modelo.producto.Bebida;
import modelo.producto.Pasta;
import modelo.producto.Pizza;
import modelo.producto.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utilidades.DataBaseConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPedidoTest {

   private Pedido pedido;
    //private Cliente cliente;

    //private LineaPedido linea1;
    //private LineaPedido linea2;
    private PedidoDao pedidoDao;

    //controladores
    private ControladorPedido controladorPedido= new ControladorPedido();
    private ControladorCliente controladorCliente= new ControladorCliente();
    private ControladorProducto controladorProducto= new ControladorProducto();

    //clientes
    Cliente cliente1 = new Cliente( "12345678A", "Juan Perez", "Calle Falsa 123", "123456789", "juan@example.com", "password",false);
    Cliente cliente2= new Cliente("45586326P", "Gustavo","calle pimienta 5","644856652","gustavo@gmail.com","abc12",true);

    //lista de lineas de pedido
     //List<LineaPedido> lineaPedido1;
    // List<LineaPedido> lineaPedido2;

    //ingrediengtes
    private Ingrediente ingredientePeperoni = new Ingrediente("peperoni", List.of("sulfito", "lacteo"));
    private Ingrediente ingredienteQueso = new Ingrediente("queso", List.of("lacteo"));
    private Ingrediente ingredientePinia = new Ingrediente("piña", List.of());
    private Ingrediente ingredientePollo = new Ingrediente("pollo", List.of("aves"));
    //lista de ingredientes
    private List<Ingrediente> ingredientesPasta1 = List.of(ingredienteQueso);
    private List<Ingrediente> ingredientePasta3 = List.of(ingredientePollo);
    private List<Ingrediente> ingredientesPasta4 = List.of(ingredienteQueso);
    private List<Ingrediente> ingredientesPizza1 = List.of(ingredientePeperoni, ingredienteQueso);
    private List<Ingrediente> ingredientesPizza2 = List.of(ingredientePeperoni, ingredienteQueso);
    private List<Ingrediente> ingredientesPizza3 = List.of(ingredienteQueso, ingredientePinia);
    //productos
    private Pasta pasta1 = new Pasta("pasta carbonara", 10, ingredientesPasta1);
    private Pasta pasta2 = new Pasta("pasta boloñesa", 8.5, ingredientesPasta4);
    private Pasta pasta3 = new Pasta("pasta Fettuccini", 13, ingredientePasta3);
    private Pizza pizza1 = new Pizza("peperoni", 7.30, Size.MEDIANA, ingredientesPizza2);
    private Pizza pizza2 = new Pizza("hawai", 12, Size.GRANDE, ingredientesPizza3);
    private Pizza pizza3 = new Pizza("margarita", 9.30, Size.PEQUEÑA, ingredientesPizza1);
    private Bebida bebida1 = new Bebida("cocacola", 1.80, Size.GRANDE);
    private Bebida bebida2 = new Bebida("cerveza", 3.50, Size.MEDIANA);
    private Bebida bebida3 = new Bebida("agua", 0.90, Size.PEQUEÑA);

    //lineas de pedido
    private LineaPedido lineaPedido1= new LineaPedido(2,pasta1);
    private LineaPedido lineaPedido2= new LineaPedido(4,pasta2);
    private LineaPedido lineaPedido3= new LineaPedido(5,pasta2);
    private LineaPedido lineaPedido4= new LineaPedido(8,bebida2);
    //pedidos
    //private Pedido pedido1=new Pedido(new Date(), EstadoPedido.PENDIENTE, cliente1,lineaPedido1);

    @Disabled
    @Test
    void borrarYcrearTablas() throws SQLException {
       // controladorPedido.borrarYcrearTablas();
        //controladorPedido.crearTablas();
        cargarInformacion();
    }

    //@BeforeEach
    public void cargarInformacion(){
       //cliente = new Cliente(1, "12345678A", "Juan Perez", "Calle Falsa 123", "123456789", "juan@example.com", "password");
       pizza1 = new Pizza("Pizza Peperoni", 10.0, Size.GRANDE, List.of());
       pizza2 = new Pizza("Pizza Margarita", 8.5, Size.PEQUEÑA, List.of());
      lineaPedido1 = new LineaPedido(1, 2, pizza1);
       lineaPedido2 = new LineaPedido(2, 1, pizza2);
      // pedido.agregarLineaPedido(linea1);
       //pedido.agregarLineaPedido(linea2);

   }

    @Disabled
    @Test
    void crearTablas() throws SQLException {
    controladorPedido.crearTablas();

    }

    @Test
    void agregarPedido() throws SQLException {
        //cliente = new Cliente(1, "12345678A", "Juan Perez", "Calle Falsa 123", "123456789", "juan@example.com", "password");
     controladorCliente.guardarCliente(cliente1);
     Pedido pedido= new Pedido(new Date(),EstadoPedido.PENDIENTE, cliente1);
     controladorPedido.agregarPedido(pedido);
     pedido.agregarLineaPedido(lineaPedido1);
     pedido.agregarLineaPedido(lineaPedido2);
     controladorPedido.crearNuevoPedido(pedido);
     Pedido pedidoGuardado = controladorPedido.obtenerPedidoPorId(pedido.getId());
     //assertNotNull(pedidoGuardado);
     assertEquals(cliente1.getId(), pedidoGuardado.getCliente().getId());
     assertEquals(EstadoPedido.PENDIENTE, pedidoGuardado.getEstado());
     assertEquals(2, pedidoGuardado.getLineasPedido().size());
     //controladorCliente.login("juan@example.com","password");
     //controladorProducto.crearNuevoPedido(pedido);

    }

    @Test
    void actualizarPedido() throws SQLException {

     controladorCliente.guardarCliente(cliente1);
     Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, cliente1);
     pedido.agregarLineaPedido(lineaPedido1);
     controladorPedido.crearNuevoPedido(pedido);
     pedido.setEstado(EstadoPedido.FINALIZADO);
     pedido.agregarLineaPedido(lineaPedido3);
     controladorPedido.actualizarPedido(pedido);


     Pedido pedidoActualizado = controladorPedido.obtenerPedidoPorId(pedido.getId());
     assertNotNull(pedidoActualizado);
     assertEquals(EstadoPedido.FINALIZADO, pedidoActualizado.getEstado());
     assertEquals(2, pedidoActualizado.getLineasPedido().size());
    }

    @Test
    void eliminarPedido() throws SQLException {
     controladorCliente.guardarCliente(cliente1);
     Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, cliente1);
     controladorPedido.crearNuevoPedido(pedido);
     controladorPedido.agregarLineaPedido(lineaPedido1);
     controladorPedido.eliminarPedido(pedido.getId());

     Pedido pedidoEliminado = controladorPedido.obtenerPedidoPorId(pedido.getId());
     assertNull(pedidoEliminado);
    }

    @Test
    void obtenerLineasPorPedido(){

     List<LineaPedido> lineas = controladorPedido.obtenerLineasPorPedido(1);

     assertNotNull(lineas, "La lista de líneas no debería ser nula");
     assertFalse(lineas.isEmpty(), "La lista de líneas no debería estar vacía");
     for (LineaPedido linea : lineas) {
      assertEquals(1, linea.getPedido_id(), "El pedido ID de la línea debería coincidir con el pedido consultado");
     }
    }

    @Test
    void obtenerPedidoPorId() {

    }

    @Test
    void crearNuevoPedido() {
    }

    @Test
    void agregarLineaPedido() throws SQLException {
     controladorCliente.guardarCliente(cliente1);


     Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, cliente1);
     pedido.agregarLineaPedido(lineaPedido1);
     controladorPedido.crearNuevoPedido(pedido);


     pedido.agregarLineaPedido(lineaPedido2);
     //controladorPedido.agregarLineaPedido(lineaPedido2);


     Pedido pedidoActualizado = controladorPedido.obtenerPedidoPorId(pedido.getId());
     assertNotNull(pedidoActualizado, "El pedido actualizado no debe ser nulo");
     assertEquals(1, pedidoActualizado.getLineasPedido().size(), "El pedido debe tener 2 líneas");

    }

 @Test
 void obtenerPedidosPorEstado() {
  Pedido pedido= new Pedido(new Date(),EstadoPedido.PENDIENTE, cliente1);

 }

 @Test
    void finalizarPedido() {
    }

    @Test
    void cancelarPedido() {
    }

    @Test
    void entregarPedido() {

    }


}