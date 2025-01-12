package controlador.dao;

import modelo.pedido.Ingrediente;
import modelo.producto.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDao {
    //creo el producto
    void save(Producto producto)throws SQLException;

    //eliminar producto por id
    void deleteProducto(int id)throws SQLException;

    //actualizar producto
    void update(Producto producto)throws SQLException;

    //obtener producto por id
    Producto obtenerProductoPorId(int id);

    //obtener todos los productos
    List<Producto> findAll();

    //obtener ingrediente por id de producto
    List<Ingrediente> obtenerIngredientesPorProducto(int idProducto);

    //obtener alergenos por id de ingrediente
    List<String> obtenerAlergenosPorIngrediente(int id_ingrediente);
}
