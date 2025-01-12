package controlador;

import controlador.dao.ProductoDao;
import controlador.dao.implement.JdbcProductoDao;
import controlador.dao.implement.jpa.JpaProductoDao;
import modelo.pedido.Ingrediente;
import modelo.producto.Producto;
import utilidades.DataBaseConfig;

import java.sql.SQLException;
import java.util.List;

public class ControladorProducto {

    private ProductoDao productoDao;

    public ControladorProducto(){
       // productoDao= new JdbcProductoDao();
        productoDao= new JpaProductoDao();
    }

    public void borrarYcrearTablas() throws SQLException {
        DataBaseConfig.dropAndCreateTables();
    }

    public void crearTablas() throws SQLException {
        DataBaseConfig.createTables();
    }

    public void guardarProducto(Producto producto) throws SQLException {
        productoDao.save(producto);
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        productoDao.update(producto);
    }

    public void borrarProductoId(int id) throws SQLException {
        productoDao.deleteProducto(id);
    }

    public List<Producto> findAll(){
        return  productoDao.findAll();
    }

    public List<Ingrediente> obtenerIngredientePorProducto(int idProducto){
        return productoDao.obtenerIngredientesPorProducto(idProducto);
    }

    public List<String> obtenerAlergenosPorIngrediente(int id_ingrediente){
        return  productoDao.obtenerAlergenosPorIngrediente(id_ingrediente);
    }

    public Producto obtenerProductoPorId(int id){
        return productoDao.obtenerProductoPorId(id);
    }
}
