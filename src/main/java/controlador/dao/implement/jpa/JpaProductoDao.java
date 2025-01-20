package controlador.dao.implement.jpa;

import controlador.dao.ProductoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jdk.jfr.Percentage;
import modelo.pedido.Ingrediente;
import modelo.pedido.Pedido;
import modelo.producto.Pasta;
import modelo.producto.Pizza;
import modelo.producto.Producto;
import org.hibernate.Hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JpaProductoDao implements ProductoDao {
    private final EntityManagerFactory entityManagerFactory;
    private static JpaProductoDao instance;
    private static List<Producto>listaProductos;

    public JpaProductoDao() {
        entityManagerFactory= Persistence.createEntityManagerFactory("default");
    }

    public static JpaProductoDao getInstance() {
        if (instance == null)
            instance = new JpaProductoDao();
        return instance;
    }

    @Override
    public void save(Producto producto) throws SQLException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Ingrediente> listaIngredientes = new ArrayList<>();

            if (producto instanceof Pizza pizza)
                listaIngredientes = pizza.getIngredientes();
            else if (producto instanceof Pasta pasta)
                listaIngredientes = pasta.getIngredientes();

            List<Ingrediente> listaManaged = new ArrayList<>();

            for (Ingrediente ingrediente : listaIngredientes) {
                Ingrediente ingredienteManaged = entityManager.find(Ingrediente.class, ingrediente.getId());
                if (ingredienteManaged == null) {
                    entityManager.persist(ingrediente);
                    ingredienteManaged = ingrediente;
                }
                ingredienteManaged.setAlergenos(ingrediente.getAlergenos());
                listaManaged.add(ingredienteManaged);
            }

            if (producto instanceof Pizza pizza) {
                pizza.setIngredientes(listaManaged);

            } else if (producto instanceof Pasta pasta) {
                pasta.setIngredientes(listaManaged);
            }
            entityManager.persist(producto);
            entityManager.getTransaction().commit();
        }

    }

    @Override
    public void deleteProducto(int id) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (obtenerProductoPorId(id) == null) {
            throw new IllegalArgumentException("No se ha podido encontrar el producto.");
        } else {
            Producto producto = obtenerProductoPorId(id);
            producto = entityManager.merge(producto);
            entityManager.remove(producto);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(producto);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Producto obtenerProductoPorId(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Producto producto = entityManager.find(Producto.class, id);
        try {
            if (producto != null) {
                if (producto instanceof Pizza) {
                    Hibernate.initialize(((Pizza) producto).getIngredientes());
                } else if (producto instanceof Pasta) {
                    Hibernate.initialize(((Pasta) producto).getIngredientes());
                }
                return producto;
            } else {
                return null;
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Producto> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Producto> query = entityManager.createQuery("SELECT p FROM Producto p", Producto.class);
            //inicializar las colecciones perezosas

            return query.getResultList();
        }
    }

    @Override
    public List<Ingrediente> obtenerIngredientesPorProducto(int idProducto) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Producto p = entityManager.find(Producto.class, idProducto);
            return switch (p) {
                case Pizza pizza -> pizza.getIngredientes();
                case Pasta pasta -> pasta.getIngredientes();
                default -> List.of();
            };
        }
    }

    @Override
    public List<String> obtenerAlergenosPorIngrediente(int id_ingrediente) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT i.alergenos FROM Ingrediente i WHERE i.id = :id", String.class)
                    .setParameter("id", id_ingrediente)
                    .getResultList();
        }
    }
}
