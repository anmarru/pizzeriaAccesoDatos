package controlador.dao.implement;

import controlador.dao.ClienteDao;
import controlador.dao.PedidoDao;
import controlador.dao.ProductoDao;
import modelo.cliente.Cliente;
import modelo.pedido.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utilidades.DataBaseConfig.*;

public class JdbcPedidoDao implements PedidoDao {

    private ClienteDao clienteDao;
    private ProductoDao productoDao;
    private Pedido pedidoActual;
    private List<Pedido> pedidos;

    public Connection getConnection()throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public JdbcPedidoDao(){
        this.clienteDao= new JdbcClienteDao();
        this.productoDao= new JdbcProductoDao();
    }


    @Override
    public void save(Pedido pedido) throws SQLException {
        try(Connection conn= getConnection()){
            PreparedStatement psPedido= conn.prepareStatement(INSERT_PEDIDO, PreparedStatement.RETURN_GENERATED_KEYS);

            psPedido.setInt(1, pedido.getId());
            psPedido.setLong(2, pedido.getCliente().getId());
            psPedido.setDate(3, new java.sql.Date(pedido.getFecha().getTime()));
           // psPedido.setFloat(4, pedido.getPrecioTotal());
            psPedido.setString(4, pedido.getEstado().name());
            psPedido.executeUpdate();

            for (LineaPedido linea : pedido.getLineasPedido()) {
                agregarLineaPedido(linea, pedido.getId());
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        if (pedido.getEstado() == EstadoPedido.ENTREGADO)
            throw new IllegalArgumentException("El pedido ya ha sido entregado");

        try(Connection conn= getConnection()){
            PreparedStatement ps= conn.prepareStatement(UPDATE_PEDIDO);
            ps.setFloat(1, pedido.getPrecioTotal());
            ps.setString(2, pedido.getEstado().name());
            ps.setInt(3, pedido.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try(Connection conn= getConnection()){
            PreparedStatement ps= conn.prepareStatement(DELETE_PEDIDO);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Pedido findById(int id) throws SQLException {
        try(Connection conn= getConnection()){
            PreparedStatement ps= conn.prepareStatement(BUSCAR_TODOS_PEDIDOS);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Crear objeto Pedido a partir del resultado
                int pedidoId = rs.getInt("id");
                int clienteId = rs.getInt("cliente_id");
                Date fecha = rs.getDate("fecha");
                float precioTotal = rs.getFloat("precio_total");
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));

                //OBTENGO EL ID DEL CLIENTE

               Cliente cliente= clienteDao.obtenerClientePorId(clienteId);

                //CREO EL PEDIDO Y ASIGNO EL CLIENTE
                Pedido pedido = new Pedido(pedidoId, cliente);  // Pasar el cliente a Pedido
                pedido.setFecha(fecha);
                pedido.setPrecioTotal(precioTotal);
                pedido.setEstado(estado);

                //OBTENGO LAS LINEAS DE PEDIDO
                List<LineaPedido> lineas = obtenerLineasPorPedido(pedidoId);
                pedido.setLineasPedido(lineas);
                return pedido;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pedido> findAll() throws SQLException {
        List <Pedido> pedidos= new ArrayList<>();
        try(Connection conn= getConnection()) {
            PreparedStatement ps = conn.prepareStatement(OBTENER_PEDIDOS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                Date fecha = rs.getDate("fecha");
                String estado = rs.getString("estado");
                int idCliente = rs.getInt("cliente");

                Cliente cliente = clienteDao.obtenerClientePorId(idCliente);
                Pedido pedido = new Pedido(id ,cliente );
                pedidos.add(pedido);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return pedidos;
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

   //Metodo para obtener el id del cliente
    /*public Cliente obtenerClientePorId(int clienteId) {
        try (Connection conn= getConnection()){
            // Consulta para obtener los datos del cliente a partir de su id
            String query = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, clienteId);  // Setear el id del cliente en la consulta
            ResultSet rs = ps.executeQuery();  // Ejecutar la consulta

            if (rs.next()) {
                // Obtener los datos del cliente desde el ResultSet
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean esAdministrador = rs.getBoolean("es_administrador");  // Asegúrate de que el campo en la base de datos es booleano

                // Crear el objeto Cliente con los datos obtenidos
                Cliente cliente = new Cliente(id, dni, nombre, direccion, telefono, email, password);
                cliente.setEsAdministrador(esAdministrador);

                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public boolean finalizarPedido(Pagable pago){
        //if();
        return true;
    }

}
