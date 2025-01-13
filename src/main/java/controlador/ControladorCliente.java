package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import controlador.dao.ClienteDao;
import controlador.dao.implement.jpa.JpaClienteDao;
import modelo.cliente.Cliente;
import modelo.pedido.Ingrediente;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pagable;
import modelo.pedido.Pedido;
import utilidades.DataBaseConfig;
import utilidades.GestorDeArchivo;

import javax.xml.bind.JAXBException;

public class ControladorCliente {
    private ClienteDao clienteDao;

    private static ControladorCliente controladorCliente;
    private ControladorPedido controladorPedido;
    private Cliente clienteActual;
    private List<Cliente> listaClientes;


    public ControladorCliente() {
        controladorPedido = ControladorPedido.getInstance();
        this.clienteActual = null;
        this.listaClientes = new ArrayList<>();

        //clienteDao= new JdbcClienteDao();//genero un cliente dao que usa la interfaz
        clienteDao= new JpaClienteDao();
    }



    public static ControladorCliente getInstance() {
        if (controladorCliente != null) {
            return controladorCliente;
        }
        return new ControladorCliente();
    }
    //-------------------METODOS PARA AGREGAR EN LA BASE DE DATOS-----------------------------
    public void borrarYcrearTablas() throws SQLException {
        DataBaseConfig.dropAndCreateTables();
    }
    public void crearTablas() throws SQLException {
        DataBaseConfig.createTables();
    }
    public void guardarCliente(Cliente cliente) throws SQLException {
        clienteDao.save(cliente);
    }

    public void actualizarCliente(Cliente cliente) throws SQLException {
        clienteDao.update(cliente);
    }
    public void borrarClienteDNI(String dni) throws SQLException {
        clienteDao.delete(dni);
    }
    public void buscarClienteEmail(String email){
        clienteDao.findByEmail(email);
    }
    public List<Cliente> obtenerTodosLosClientes(){
        return clienteDao.findAll();
    }

    public Cliente obtenerClienteId(int id){
        return clienteDao.obtenerClientePorId(id);
    }

    public void deleteId(int id) throws SQLException {
        clienteDao.deleteId(id);
    }

    public Cliente login(String email, String password){
        return clienteDao.login(email,password);
    }
    //hacer funcion que sea loguear que obtenga el email y la contraseña para obtener el cliente
    //busco el email del clientes en la lista de clientes y compruebo su contraseña para poder obtenerlo

    //-----------------------------------------------------------------------
    public boolean agregarLieaPedido(LineaPedido lineaPedido) {
        return controladorPedido.agregarLineaPedido(lineaPedido);

    }

    /*public boolean registrarCliente(int id, String dni, String nombre, String direccion, String telefono, String email,
                                    String password) {

        Cliente cliente = null;

        for (Cliente cliente2 : listaClientes) {
            if (cliente2.getEmail().equals(email)) {
                System.out.println("El cliente ya esta registrado ");
                return false;
            }
        }
        cliente = new Cliente(id, dni, nombre, direccion, telefono, email, password);
        listaClientes.add(cliente);
        System.out.println("Cliente registrado ");
        return true;

    }

    public boolean autenticarCliente(String email, String password) {

        for (Cliente cliente : listaClientes) {
            if (cliente.getPassword().equals(password) && cliente.getEmail().equals(email)) {
                clienteActual = cliente;
                System.out.println("autenticacion correcta ");

                controladorPedido.setPedidoactual(new Pedido(clienteActual.getPedidos().size(), clienteActual));
                return true;
            }

        }
        System.out.println("Autenticacion erronea ");
        return false;

    }*/


    public boolean finalizarPedido(Pagable pago) {
        return controladorPedido.finalizarPedido(pago);
    }


    public boolean cancelarPedido() {
        clienteActual.agregarPedidos(controladorPedido.cancelarPedido());
        controladorPedido.setPedidoactual(new Pedido(clienteActual.getPedidos().size(), clienteActual));
        return true;

    }


    public boolean recibirPedido() {
        clienteActual.agregarPedidos(controladorPedido.entregarPedido());
        return true;
    }

    public static ControladorCliente getControladorCilente() {
        return controladorCliente;
    }

    public static void setControladorCilente(ControladorCliente controladorCliente) {
        ControladorCliente.controladorCliente = controladorCliente;
    }

    public ControladorPedido getControladorPedido() {
        return controladorPedido;
    }

    public void setControladorPedido(ControladorPedido controladorPedido) {
        this.controladorPedido = controladorPedido;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }


    public List<Cliente> leerArchivoTXT() throws IOException {

        return GestorDeArchivo.leerCliente();

    }

    public void exportarArchivoClientesXML(List<Cliente> listaClientes) throws JAXBException {
        GestorDeArchivo.exportarClienteAxml(listaClientes);
    }

    public List<Cliente> importarArchivoClientesXML() throws JAXBException {
        return GestorDeArchivo.importarClientesDesdeArchivoXML();
    }

    public void exportarArchivoIngredientesCSV(List<Ingrediente> listaDeIngredientes) throws CsvRequiredFieldEmptyException, FileNotFoundException, CsvDataTypeMismatchException {
        GestorDeArchivo.exportarIngredienteCSV(listaDeIngredientes);
    }

    public List<Ingrediente> importarArchivoIngredienteCSV() throws IOException {
        return GestorDeArchivo.importarIngredientesDesdeCSV();
    }
}
