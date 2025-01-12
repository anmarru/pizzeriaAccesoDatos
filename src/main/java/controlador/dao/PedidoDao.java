package controlador.dao;

import modelo.pedido.LineaPedido;
import modelo.pedido.Pedido;
import modelo.producto.Producto;

import java.sql.SQLException;
import java.util.List;

public interface PedidoDao {

    void save(Pedido pedido) throws SQLException;

    void update(Pedido pedido) throws SQLException;

    void delete(int id) throws SQLException;

    Pedido findById(int id) throws SQLException;

    List<Pedido> findAll() throws SQLException;

    List<Pedido> obtenerPedidosPorCliente(int  clienteId);

    List<Pedido> obtenerPedidosPorEstado(String estado);

    List<LineaPedido> obtenerLineasPorPedido(int  pedidoId);

    void agregarLineaPedido(LineaPedido lineaPedido, int  pedidoId);
}
