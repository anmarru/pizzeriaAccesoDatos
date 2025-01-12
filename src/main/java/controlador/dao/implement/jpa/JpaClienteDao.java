package controlador.dao.implement.jpa;

import controlador.dao.ClienteDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
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
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.dni= :dni", Cliente.class
            ).setParameter("dni", dni);
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
            return query.getResultList();
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
        if(obtenerClientePorId(id)== null){
            throw new IllegalArgumentException("el cliente no ha sido encontrado");
        }else{
            Cliente cliente= obtenerClientePorId(id);
            cliente= entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
