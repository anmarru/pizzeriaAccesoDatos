package controlador;

import controlador.dao.PedidoDao;
import controlador.dao.implement.JdbcPedidoDao;
import modelo.cliente.Cliente;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pedido;
import modelo.producto.Pizza;
import modelo.producto.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.DataBaseConfig;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPedidoTest {
    private Pedido pedido;
    private Cliente cliente;
    private Pizza pizza1;
    private Pizza pizza2;
    private LineaPedido linea1;
    private LineaPedido linea2;
    private PedidoDao pedidoDao;

    private ControladorPedido controladorPedido= new ControladorPedido();

    private List<Pedido> pedidos;
    private Pedido pedidoActual;

    @Test
    void borrarYcrearTablas() throws SQLException {
       // controladorPedido.borrarYcrearTablas();
        controladorPedido.crearTablas();
        cargarInformacion();
    }

    @BeforeEach
    public void cargarInformacion(){
       cliente = new Cliente(1, "12345678A", "Juan Perez", "Calle Falsa 123", "123456789", "juan@example.com", "password");
       pizza1 = new Pizza("Pizza Peperoni", 10.0, Size.GRANDE, List.of());
       pizza2 = new Pizza("Pizza Margarita", 8.5, Size.PEQUEÑA, List.of());
       //linea1 = new LineaPedido(1, 2, pizza1);
      // linea2 = new LineaPedido(2, 1, pizza2);
       pedido.agregarLineaPedido(linea1);
       pedido.agregarLineaPedido(linea2);

   }

    @Test
    void crearTablas() throws SQLException {
    controladorPedido.crearTablas();

    }

    @Test
    void agregarPedido() {

    }

    @Test
    void actualizarPedido() {
    }

    @Test
    void eliminarPedido() {
    }

    @Test
    void obtenerPedidoPorId() {
    }

    @Test
    void crearNuevoPedido() {
    }

    @Test
    void agregarLineaPedido() {

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