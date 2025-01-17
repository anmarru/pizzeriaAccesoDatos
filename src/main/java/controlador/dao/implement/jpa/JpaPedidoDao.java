package controlador.dao.implement.jpa;

import controlador.dao.PedidoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.cliente.Cliente;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pedido;
import org.hibernate.Hibernate;

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
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Pedido pedido= entityManager.find(Pedido.class, id);
        if(pedido== null){
            throw new IllegalArgumentException("el pedido no ha sido encontrado");
        }else{
            //se elimina la entidad que gestiona el entity
            entityManager.remove(pedido);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public Pedido findById(int id) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //entityManager.getTransaction().begin();
        Pedido pedido= entityManager.find(Pedido.class, id);
        if(pedido !=null){
            Hibernate.initialize((pedido.getLineasPedido()));//si se pone lazy hay que inicializarlo
        }
        entityManager.close();
        return pedido;
    }

    @Override
    public List<Pedido> findAll() throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Pedido> query = entityManager.createQuery("SELECT p FROM Pedido p",Pedido.class);
            List<Pedido> pedidos= query.getResultList();
            //inicializar las colecciones perezosas
            for (Pedido pedido : pedidos) {
                pedido.getLineasPedido().size();
            }
            return pedidos;
        }
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
