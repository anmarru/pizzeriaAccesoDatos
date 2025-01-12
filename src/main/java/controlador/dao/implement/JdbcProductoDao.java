package controlador.dao.implement;

import controlador.dao.ProductoDao;
import modelo.pedido.Ingrediente;
import modelo.producto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utilidades.DataBaseConfig.*;

public class JdbcProductoDao implements ProductoDao {

    //CREO LA CONEXION
    public Connection getConnection()throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public void save(Producto producto) {
        String getAlergenoId = "SELECT id FROM alergeno WHERE nombre = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); //inicio la transaccion

            try (
                    PreparedStatement psProducto = conn.prepareStatement(INSET_PRODUCTO, Statement.RETURN_GENERATED_KEYS)
            ) {
                // Definir una lista de ingredientes (si es una bebida, estará vacía)
                List<Ingrediente> ingredientes = new ArrayList<>();

                //inserto el producto
                psProducto.setString(1, producto.getNombre());
                psProducto.setDouble(2, producto.getPrecio());

                switch (producto) {
                    case Pizza pizza -> {
                        psProducto.setString(3, "pizza");
                        psProducto.setString(4, pizza.getSize().toString());
                        ingredientes = pizza.getIngredientes();
                    }
                    case Pasta pasta -> {
                        psProducto.setString(3, "pasta");
                        psProducto.setString(4, null);
                        ingredientes = pasta.getIngredientes();
                    }
                    case Bebida bebida -> {
                        psProducto.setString(3, "bebida");
                        psProducto.setString(4, bebida.getSize().toString());
                    }
                    default -> {
                    }
                }

                psProducto.executeUpdate();


                 ResultSet generateKeys = psProducto.getGeneratedKeys();
                    if (generateKeys.next()) {
                        producto.setId(generateKeys.getInt(1));
                    }


                int idIProducto = producto.getId();//obtengo el id del producto

                //insertar ingredientes
                for (Ingrediente ingrediente : ingredientes) {


                    try (PreparedStatement psIngrediente = conn.prepareStatement(INSERT_INGREDIENTE, Statement.RETURN_GENERATED_KEYS)) {
                        psIngrediente.setString(1, ingrediente.getNombre());
                        psIngrediente.setInt(2, idIProducto);
                        psIngrediente.executeUpdate();

                       ResultSet rsIngrediente = psIngrediente.getGeneratedKeys();
                            rsIngrediente.next();
                            //obtengo el id de ingrediente
                            int idIngrediente = rsIngrediente.getInt(1);


                            for (String alergeno : ingrediente.getAlergenos()) {
                                //variable para id alergeno  si ya existe
                                int idAlergeno;
                                try (PreparedStatement psAlergeno = conn.prepareStatement(getAlergenoId)) {
                                    psAlergeno.setString(1, alergeno);
                                    ResultSet rsAlergeno = psAlergeno.executeQuery();

                                        if (rsAlergeno.next()) {
                                            //id del alergeno que ya existe
                                            idAlergeno = rsAlergeno.getInt("id");

                                        } else {
                                            //inserto el alergeno si no existe
                                            try (PreparedStatement psInsertAlergeno = conn.prepareStatement(INSERT_ALERGENO, Statement.RETURN_GENERATED_KEYS)) {
                                                psInsertAlergeno.setString(1, alergeno);
                                                psInsertAlergeno.executeUpdate();

                                                try (ResultSet rsnuevoAlergeno = psInsertAlergeno.getGeneratedKeys()) {
                                                    rsnuevoAlergeno.next();
                                                    idAlergeno = rsnuevoAlergeno.getInt(1);
                                                }
                                            }
                                        }

                                }

                               PreparedStatement psIngredienteProducto = conn.prepareStatement(INSERT_INGREDIENTE_PRODUCTO);
                                    psIngredienteProducto.setInt(1, idIngrediente);
                                    psIngredienteProducto.setInt(2, idIProducto);
                                    psIngredienteProducto.executeUpdate();



                                PreparedStatement psAlergenoRelacion = conn.prepareStatement(INSERT_INGREDIENTE_ALERGENO);
                                    psAlergenoRelacion.setInt(1, idIngrediente);
                                    psAlergenoRelacion.setInt(2, idAlergeno);
                                    psAlergenoRelacion.executeUpdate();

                            }

                    }
                }

                conn.commit();//confirmo la transacción
            } catch (SQLException e) {
                conn.rollback();//revierto la transaccion si ocurre algun error
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void deleteProducto(int id) {
        try(Connection conn= getConnection();
            PreparedStatement stProducto= conn.prepareStatement(DELETE_PRODUCTO)) {

            stProducto.setInt(1,id);
            stProducto.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Producto producto) {
        try(Connection conn=getConnection();
            PreparedStatement psProducto = conn.prepareStatement(UPDATE_PRODUCTO)){
             // Definir una lista de ingredientes (si es una bebida, estará vacía)

             psProducto.setString(1, producto.getNombre());
             psProducto.setDouble(2, producto.getPrecio());

             switch (producto) {
                 case Pizza pizza -> {
                     psProducto.setString(3, "pizza");
                     psProducto.setString(4, pizza.getSize().toString());
                 }
                 case Pasta pasta -> {
                     psProducto.setString(3, "pasta");
                     psProducto.setString(4, null);
                 }
                 case Bebida bebida -> {
                     psProducto.setString(3, "bebida");
                     psProducto.setString(4, bebida.getSize().toString());
                 }
                 default -> {
                 }
             }

             psProducto.setInt(5, producto.getId());
             psProducto.executeUpdate();

             //elimino ingredientes
             /*for (Ingrediente ingrediente : producto.getIngredientes()) {
                 PreparedStatement psDeleteAlergenos = conn.prepareStatement(DELETE_INGREDIENTE_ALERGENO);
                 psDeleteAlergenos.setInt(1, ingrediente.getId());
                 psDeleteAlergenos.executeUpdate();
             }

             PreparedStatement psdeleteIngredientes = conn.prepareStatement(DELETE_INGREDIENTE);
             psdeleteIngredientes.setInt(1, producto.getId());
             psdeleteIngredientes.executeUpdate();*/

             //reinserto ingredientes y alergenos
             //save(producto);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public Producto obtenerProductoPorId(int id) {

        Producto producto= null;
        try(Connection conn= getConnection()) {
            //obtengo producto
            PreparedStatement psProducto= conn.prepareStatement(BUSCAR_TODOS_PRODUCTOS);
            List<Ingrediente> ingredientes= new ArrayList<>();
            ResultSet rsProducto= psProducto.executeQuery();

            if(rsProducto.next()){
                producto = switch (rsProducto.getString("tipo")) {
                    case "pizza" -> new Pizza(rsProducto.getString("nombre"), rsProducto.getDouble("precio"), Size.valueOf(rsProducto.getString("tamanio")), ingredientes);
                    case "pasta" -> new Pasta(rsProducto.getString("nombre"), rsProducto.getDouble("precio"), ingredientes);
                    case "bebida" -> new Bebida(rsProducto.getString("nombre"), rsProducto.getDouble("precio"), Size.valueOf(rsProducto.getString("tamanio")));
                    default -> null;
                };


                 PreparedStatement psIngrediente= conn.prepareStatement(BUSCAR_TODOS_INGREDIENTES);
                    psIngrediente.setInt(1,id);
                    ResultSet rsIngrediente= psIngrediente.executeQuery();

                   while (rsIngrediente.next()){
                       Ingrediente ingrediente = new Ingrediente(
                               rsIngrediente.getInt("id"),
                               rsIngrediente.getString("nombre")
                       );

                       //obtengo alergenos del ingrediente
                       PreparedStatement psAlergenos= conn.prepareStatement(BUSCAR_TODOS_ALERGENOS);
                       psAlergenos.setInt(1,ingrediente.getId());
                       ResultSet rsAlergenos=psAlergenos.executeQuery();

                       while (rsAlergenos.next()){
                           //agrego el nombre del alergeno a la lista de alergenos del ingrediente
                        ingrediente.getAlergenos().add(rsAlergenos.getString("nombre"));
                       }
                       //agrego el ingrediente al producto
                       ingredientes.add(ingrediente);
                   }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos= new ArrayList<>();

        try(Connection conn= getConnection();
            PreparedStatement ps= conn.prepareStatement(BUSCAR_TODOS_PRODUCTOS)) {
            ResultSet rs= ps.executeQuery();

            while (rs.next()){
                Producto producto;

                if(rs.getString("tipo").equals("pizza"))
                    producto= new Pizza(rs.getString("nombre"), rs.getDouble("precio"), Size.valueOf(rs.getString("tamanio")), new ArrayList<>());
                else if(rs.getString("tipo").equals("pasta"))
                    producto= new Pasta(rs.getString("nombre"), rs.getDouble("precio"), new ArrayList<>());
                else if(rs.getString("tipo").equals("bebida"))
                    producto= new Bebida(rs.getString("nombre"), rs.getDouble("precio"), Size.valueOf(rs.getString("tamanio")));
                else continue; //si no encuentra continua

                producto.setId(rs.getInt("id"));

                //cargo los ingredientes para el producto
                if(producto instanceof Pasta || producto instanceof Pizza){
                    List<Ingrediente> ingredientes= obtenerIngredientesPorProducto(producto.getId());//uso la funcion
                    ingredientes.forEach(System.out::println);
                    for(Ingrediente ingrediente: ingredientes){
                        //cargo los alergenos de cada ingrediente
                        List<String> alergenos= obtenerAlergenosPorIngrediente(ingrediente.getId());//uso la funcion
                        ingrediente.setAlergenos(alergenos);
                    }
                    //asigno los ingredientes al producto
                    if(producto instanceof Pizza pizza){
                        pizza.setIngredientes(ingredientes);
                    }else if (producto instanceof Pasta pasta){
                        pasta.setIngredientes(ingredientes);
                    }
                }
                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public List<Ingrediente> obtenerIngredientesPorProducto(int idProducto) {
        List<Ingrediente> ingredientes = new ArrayList<>();

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(SELECT_PRUEBA);

            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                ingredientes.add(ingrediente);
                //ingredientes.forEach(System.out::println);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredientes;
    }

    @Override
    public List<String> obtenerAlergenosPorIngrediente(int id_ingrediente) {
        List<String> alergenos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(BUSCAR_TODOS_ALERGENOS)) {

            ps.setInt(1, id_ingrediente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alergenos.add(rs.getString("nombre"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener alérgenos", e);
        }

        return alergenos;

    }


}
