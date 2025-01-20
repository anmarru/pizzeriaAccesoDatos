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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Cliente managedCliente = entityManager.find(Cliente.class, pedido.getCliente().getId());

            if (managedCliente == null)
                entityManager.persist(pedido.getCliente());
            else
                pedido.setCliente(managedCliente);
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(pedido);
            entityManager.getTransaction().commit();

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
        //entityManager.close();
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
    public void agregarLineaPedido(LineaPedido lineaPedido, int pedidoId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            //buscar el pedido al que se agregará la línea
            Pedido pedido = entityManager.find(Pedido.class, pedidoId);
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido no encontrado con id: " + pedidoId);
            }


            lineaPedido.setPedido_id(pedido.getId());
            pedido.getLineasPedido().add(lineaPedido);


            entityManager.persist(lineaPedido);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Error al agregar línea de pedido", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(int clienteId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            TypedQuery<Pedido> query = entityManager.createQuery(
                    "SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId", Pedido.class
            );
            query.setParameter("clienteId", clienteId);
            List<Pedido> pedidos = query.getResultList();

            // Inicializar las colecciones perezosas si es necesario
            for (Pedido pedido : pedidos) {
                pedido.getLineasPedido().size();
            }

            return pedidos;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            TypedQuery<Pedido> query = entityManager.createQuery(
                    "SELECT p FROM Pedido p WHERE p.estado = :estado", Pedido.class
            );
            query.setParameter("estado", estado);
            List<Pedido> pedidos = query.getResultList();


            for (Pedido pedido : pedidos) {
                pedido.getLineasPedido().size();
            }

            return pedidos;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<LineaPedido> obtenerLineasPorPedido(int pedidoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Buscar el pedido y obtener sus líneas de pedido
            Pedido pedido = entityManager.find(Pedido.class, pedidoId);
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido no encontrado con id: " + pedidoId);
            }

            // Inicializar las líneas de pedido si están en modo Lazy
            List<LineaPedido> lineas = pedido.getLineasPedido();
            lineas.size();

            return lineas;
        } finally {
            entityManager.close();
        }
    }


}
