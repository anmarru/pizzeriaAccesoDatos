package controlador.dao.implement.jpa;

import controlador.dao.ProductoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jdk.jfr.Percentage;
import modelo.pedido.Ingrediente;
import modelo.producto.Pasta;
import modelo.producto.Pizza;
import modelo.producto.Producto;
import org.hibernate.Hibernate;

import java.sql.SQLException;
import java.util.List;

public class JpaProductoDao implements ProductoDao {
    private final EntityManagerFactory entityManagerFactory;
    private static JpaProductoDao instance;

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
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            if(producto instanceof Pizza pizza){
                for(Ingrediente ingrediente : pizza.getIngredientes()){
                    if(ingrediente.getId() !=0){
                        entityManager.merge(ingrediente);
                    }else {
                        entityManager.persist(ingrediente);
                    }
                }

            } else if (producto instanceof Pasta pasta) {
                for(Ingrediente ingrediente: pasta.getIngredientes()){
                   if(ingrediente.getId() !=0){
                       entityManager.merge(ingrediente);
                   }else {
                       entityManager.persist(ingrediente);
                   }
                }
            }
            entityManager.persist(producto);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
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
        return List.of();
    }

    @Override
    public List<Ingrediente> obtenerIngredientesPorProducto(int idProducto) {
        return List.of();
    }

    @Override
    public List<String> obtenerAlergenosPorIngrediente(int id_ingrediente) {
        return List.of();
    }
}
