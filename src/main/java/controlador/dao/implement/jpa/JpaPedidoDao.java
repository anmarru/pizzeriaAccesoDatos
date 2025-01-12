package controlador.dao.implement.jpa;

import controlador.dao.PedidoDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pedido;

import java.sql.SQLException;
import java.util.List;

public class JpaPedidoDao implements PedidoDao {

    private final EntityManagerFactory entityManagerFactory;
    private static  JpaPedidoDao instance;

    public JpaPedidoDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public static JpaPedidoDao getInstance(){
        if(instance == null)
            instance = new JpaPedidoDao();
        return instance;
    }

    @Override
    public void save(Pedido pedido) throws SQLException {

    }

    @Override
    public void update(Pedido pedido) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public Pedido findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Pedido> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(int clienteId) {
        return List.of();
    }

    @Override
    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        return List.of();
    }

    @Override
    public List<LineaPedido> obtenerLineasPorPedido(int pedidoId) {
        return List.of();
    }

    @Override
    public void agregarLineaPedido(LineaPedido lineaPedido, int pedidoId) {

    }
}
