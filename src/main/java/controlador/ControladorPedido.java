package controlador;

import controlador.dao.PedidoDao;
import controlador.dao.ProductoDao;
import controlador.dao.implement.JdbcPedidoDao;
import controlador.dao.implement.JdbcProductoDao;
import controlador.dao.implement.jpa.JpaPedidoDao;
import modelo.pedido.EstadoPedido;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pagable;
import modelo.pedido.Pedido;
import utilidades.DataBaseConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedido {

    private PedidoDao pedidoDao;

    private static ControladorPedido instancia;

    private Pedido pedidoactual;
    private List<Pedido> pedidos;

    public ControladorPedido() {
        this.pedidoactual = null;
        this.pedidos = new ArrayList<>();

       // pedidoDao= new JdbcPedidoDao();//genero un cliente dao que usa la interfaz
        pedidoDao= new JpaPedidoDao();
    }

    public static ControladorPedido getInstance() {
        if (instancia == null) {
            instancia = new ControladorPedido();
        }
        return instancia;
    }

    //-----------------------------METODOS PARA AGREGAR A LA BASE DE DATOS -----------------------------------

    public void borrarYcrearTablas() throws SQLException {
        DataBaseConfig.dropAndCreateTables();
    }
    public void crearTablas() throws SQLException {
        DataBaseConfig.createTables();
    }

    public void agregarPedido(Pedido pedido) throws SQLException {
        pedidoDao.save(pedido);
    }

    public void actualizarPedido(Pedido pedido) throws SQLException {
        pedidoDao.update(pedido);
    }

    public void eliminarPedido(int id) throws SQLException {
        pedidoDao.delete(id);
    }

    public Pedido obtenerPedidoPorId(int id) throws SQLException {
        return pedidoDao.findById(id);
    }


    public void crearNuevoPedido(Pedido pedido) {
        pedidoactual = pedido;
    }

    public boolean agregarLineaPedido(LineaPedido lineaPedido) {
        if (pedidoactual == null) {
            System.out.println("No hay pedidos para añadir linea ");
            return false;
        }

        pedidoactual.agregarLineaPedido(lineaPedido);
        return true;
    }

    public boolean finalizarPedido(Pagable pagable) {

        if (pedidoactual.getLineasPedido().isEmpty() || pedidoactual == null) {
            System.out.println("no hay lineas en el pedido ");
        }
        pagable.pagar(pedidoactual.getPrecioTotal());
        pedidoactual.setEstado(EstadoPedido.FINALIZADO);
        pedidos.add(pedidoactual);
        //pedidoactual= null;
        return true;

    }

    public List<LineaPedido> obtenerLineasPorPedido(int id){
       return pedidoDao.obtenerLineasPorPedido(id);
    }

    public Pedido cancelarPedido() {
        if (pedidoactual == null) {
            System.out.println("No hay pedidos activos ");

        }

        if (pedidoactual.getEstado() == EstadoPedido.ENTREGADO) {
            System.out.println("Pedido entregado , no se puede cancelar ");

        }

        pedidoactual.setEstado(EstadoPedido.CANCELADO);
        System.out.println("Pedido cancelado ");

        return pedidoactual;
    }

    public Pedido entregarPedido() {
        if (pedidoactual == null || pedidoactual.getEstado() != EstadoPedido.FINALIZADO) {
            System.out.println("No hay pedido finalizado para ser entregado ");

        }
        pedidoactual.setEstado(EstadoPedido.ENTREGADO);
        System.out.println("Pedido entregado");

        return pedidoactual;
    }



    public static ControladorPedido getInstancia() {
        return instancia;
    }

    public static void setInstancia(ControladorPedido instancia) {
        ControladorPedido.instancia = instancia;
    }

    public Pedido getPedidoactual() {
        return pedidoactual;
    }

    public void setPedidoactual(Pedido pedidoactual) {
        this.pedidoactual = pedidoactual;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
