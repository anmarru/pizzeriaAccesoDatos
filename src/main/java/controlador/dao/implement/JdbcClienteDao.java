package controlador.dao.implement;

import controlador.dao.ClienteDao;
import modelo.cliente.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utilidades.DataBaseConfig.*;

public class JdbcClienteDao implements ClienteDao {

    //CREO LA CONEXION
    public Connection getConnection()throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public void save(Cliente cliente) throws SQLException {
        try(Connection conn= getConnection();
            PreparedStatement estatementCliente=conn.prepareStatement(INSERT_CLIENTES, Statement.RETURN_GENERATED_KEYS)//genero el id
        ){
            estatementCliente.setString(1,cliente.getDni());
            estatementCliente.setString(2,cliente.getNombre());
            estatementCliente.setString(3,cliente.getDireccion());
            estatementCliente.setString(4,cliente.getTelefono());
            estatementCliente.setString(5,cliente.getEmail());
            estatementCliente.setString(6,cliente.getPassword());
            estatementCliente.setBoolean(7,cliente.isEsAdministrador());
            estatementCliente.executeUpdate();
            //obtengo el id
            try(ResultSet generatedKeys= estatementCliente.getGeneratedKeys()){
                if(generatedKeys.next()){
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String dni) throws SQLException {
        try(Connection conn= getConnection();
        PreparedStatement statementCliente=conn.prepareStatement(DELETE_CLIENTES) ){
            statementCliente.setString(1, dni);
            statementCliente.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        try(Connection conn= getConnection();
            PreparedStatement statementCliente=conn.prepareStatement(UPDATE_CLIENTES) ){
            statementCliente.setString(1,cliente.getDni());
            statementCliente.setString(2,cliente.getNombre());
            statementCliente.setString(3,cliente.getDireccion());
            statementCliente.setString(4,cliente.getTelefono());
            statementCliente.setString(5,cliente.getEmail());
            statementCliente.setString(6,cliente.getPassword());
            statementCliente.setBoolean(7,cliente.isEsAdministrador());
            statementCliente.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cliente findByEmail(String email) {
        try(Connection conn= getConnection();
        PreparedStatement statement= conn.prepareStatement(BUSCAR_CLIENTE_EMAIL)){
            statement.setString(1,email);
            ResultSet rs=statement.executeQuery();
            if(rs.next()){
                Cliente cliente= new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        email,
                        rs.getString("password"),
                        rs.getBoolean("esAdministrador"));
                //despues de crear el cliente obtengo el id
                cliente.setId(rs.getInt("id"));
                return cliente;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Cliente> findAll() {
        //creo la lista donde se almacenan todos los clientes
        List<Cliente> clientes= new ArrayList<>();

        try(Connection conn= getConnection();
        PreparedStatement stmt= conn.prepareStatement(BUSCAR_TODOS_CLIENTES)){
            ResultSet rs= stmt.executeQuery();
            while (rs.next()){
                Cliente cliente= new Cliente(
                  rs.getString("dni"),
                  rs.getString("nombre"),
                  rs.getString("direccion"),
                  rs.getString("telefono"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getBoolean("esAdministrador")
                );
                cliente.setId(rs.getInt("id"));
                //agrego los clientes a la lista
                clientes.add(cliente);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return clientes;
    }

    @Override
    public Cliente obtenerClientePorId(int clienteId) {
        try (Connection conn= getConnection()){

            String query = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener los datos del cliente desde el ResultSet
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean esAdministrador = rs.getBoolean("es_administrador");


                Cliente cliente = new Cliente(id, dni, nombre, direccion, telefono, email, password);
                cliente.setEsAdministrador(esAdministrador);

                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteId(int id) throws SQLException {

    }
}
