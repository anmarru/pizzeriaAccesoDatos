package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.cliente.Cliente;
import modelo.pedido.LineaPedido;
import modelo.pedido.Pagable;
import modelo.pedido.Pedido;
import modelo.producto.Producto;

public class ControladorCilente {
    private static ControladorCilente controladorCilente;
    private ControladorPedido controladorPedido;

    private Cliente clienteActual;
    private List<Cliente> listaClientes;


    

    public ControladorCilente( ) {
       controladorPedido= ControladorPedido.getInstance();
       this.clienteActual=null;
       this.listaClientes=new ArrayList<>();
    }


    public static ControladorCilente getInstance(){
        if(controladorCilente !=null){
            return controladorCilente;
        }
        return new ControladorCilente();
    }


    public boolean agregarLieaPedido(LineaPedido lineaPedido){
        return controladorPedido.agregarLineaPedido(lineaPedido);
        
    }

    public boolean registrarCliente(int id, String dni, String nombre, String direccion, String telefono, String email,
    String password, boolean esAdministrador){

        Cliente cliente= null;

        for (Cliente cliente2 : listaClientes) {
            if(cliente2.getEmail().equals(email)){
                System.out.println("El cliente ya esta registrado ");
                return false;
            }
        }
       cliente= new Cliente(id,dni,nombre,direccion,telefono,email,password,esAdministrador);
        listaClientes.add(cliente);
        System.out.println("Cliente registrado ");
        return  true;
        
    }

    public boolean autenticarCliente(String email, String password){
        
        for (Cliente cliente : listaClientes) {
            if(cliente.getPassword().equals(password) && cliente.getEmail().equals(email)){
                clienteActual=cliente;
                System.out.println("autenticacion correcta ");

                controladorPedido.setPedidoactual(new Pedido(clienteActual.getPedidos().size(),clienteActual));
                return true;
            }

        }
        System.out.println("Autenticacion erronea ");
        return  false;

    }


    public boolean finalizarPedido(Pagable pago){
        return controladorPedido.finalizarPedido(pago);
    }


    public boolean cancelarPedido(){
        clienteActual.agregarPedidos(controladorPedido.cancelarPedido());
        controladorPedido.setPedidoactual(new Pedido(clienteActual.getPedidos().size(),clienteActual));
        return true;

    }


    public boolean recibirPedido(){
        clienteActual.agregarPedidos(controladorPedido.entregarPedido());
        return true;
    }

    public static ControladorCilente getControladorCilente() {
        return controladorCilente;
    }

    public static void setControladorCilente(ControladorCilente controladorCilente) {
        ControladorCilente.controladorCilente = controladorCilente;
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
}
