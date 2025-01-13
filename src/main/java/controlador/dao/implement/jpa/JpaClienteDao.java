package controlador.dao.implement.jpa;

import controlador.dao.ClienteDao;
import jakarta.persistence.*;
import modelo.cliente.Cliente;
import org.hibernate.Hibernate;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class JpaClienteDao implements ClienteDao {

    private final EntityManagerFactory entityManagerFactory;
    private static JpaClienteDao instance;

    public JpaClienteDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public static JpaClienteDao getInstance() {
        if (instance == null)
            instance = new JpaClienteDao();
        return instance;
    }

    @Override
    public void save(Cliente cliente) throws SQLException {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(String dni) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            //Cliente cliente = entityManager.find(Cliente.class, dni);//hacer quiery
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.dni= :dni", Cliente.class)
                    .setParameter("dni", dni);
            Cliente cliente = query.getResultStream().findFirst().orElse(null);
            if (cliente != null) {
                entityManager.remove(cliente);//revoque elimina las entidades de la base de datos
                entityManager.getTransaction().commit();
            } else {
                entityManager.getTransaction().rollback();
            }
            throw new SQLException("Cliente con DNI " + dni + " no encontrado.");

        }


    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Cliente findByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);
            query.setParameter("email", email);
            Cliente cliente= query.getResultStream().findFirst().orElse(null);
            if (cliente !=null){
               Hibernate.initialize(( cliente.getPedidos()));
            }
            entityManager.close();
            return cliente;



    }

    @Override
    public List<Cliente> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
            List<Cliente> clientes= query.getResultList();
            //inicializar las colecciones perezosas
            for (Cliente cliente : clientes) {
                cliente.getPedidos().size(); // Acceder a la colección para inicializarla
            }
            return clientes;
        }
    }

    @Override
    public Cliente obtenerClientePorId(int clienteId) {
       EntityManager entityManager = entityManagerFactory.createEntityManager();
            //entityManager.getTransaction().begin();
            Cliente cliente= entityManager.find(Cliente.class, clienteId);
            if(cliente !=null){
                Hibernate.initialize((cliente.getPedidos()));//si se pone lazy hay que inicializarlo
            }
            entityManager.close();
           return cliente;

    }

    @Override
    public void deleteId(int id) throws SQLException {
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Cliente cliente= entityManager.find(Cliente.class,id);
        if(cliente== null){
            throw new IllegalArgumentException("el cliente no ha sido encontrado");
        }else{
            //se elimina la entidad que gestiona el entity
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public Cliente login(String email, String password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Crear la consulta y establecer parámetros
            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT c FROM Cliente c WHERE c.email = :email AND c.password = :password",
                    Cliente.class
            );
            query.setParameter("email", email);
            query.setParameter("password", password);

            // Obtener el resultado antes de cerrar el EntityManager
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Manejar el caso donde no se encuentra un cliente con las credenciales proporcionadas
            return null;
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }
    }


}
