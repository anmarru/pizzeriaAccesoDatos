package controlador;


import jakarta.persistence.EntityManager;
import modelo.cliente.Cliente;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ControladorClienteTest {
    private ControladorCliente controladorCliente= new ControladorCliente();



    @Test
    @Disabled
    public void borrarYcrearTablas() throws SQLException {
        crearTablas();
        controladorCliente.borrarYcrearTablas();
    }

    @Test
    @Disabled
    public void crearTablas() throws SQLException {
        controladorCliente.crearTablas();
    }

    @Test
    public void guardarCliente() throws SQLException {

        Cliente cliente1= new Cliente("96586326Q", "Ana","calle falsa 45","699856652","ana@gmail.com","123456",false);
        Cliente cliente2= new Cliente("45586326P", "Gustavo","calle pimienta 5","644856652","gustavo@gmail.com","abc12",true);
        controladorCliente.guardarCliente(cliente1);
        assertEquals(1, cliente1.getId());
        controladorCliente.guardarCliente(cliente2);
        assertEquals(2,cliente2.getId());

    }

    @Test
    public void actualizarCliente() throws SQLException {
        Cliente cliente1 = new Cliente("96586326Q", "Ana", "calle falsa 45", "699856652", "ana@gmail.com", "123456", false);
        controladorCliente.guardarCliente(cliente1);
        cliente1=controladorCliente.obtenerClienteId(1);
        cliente1.setNombre("Ana Ruiz");
        controladorCliente.actualizarCliente(cliente1);
        assertEquals("Ana Ruiz", cliente1.getNombre());
        System.out.println(cliente1);
    }

    @Test
    public void borrarClienteDNI() throws SQLException {
        Cliente cliente = new Cliente("45586326P", "Gustavo", "calle pimienta 5", "644856652", "gustavo@gmail.com", "abc12", true);
        Cliente cliente1= new Cliente("96586326Q", "Ana Ruiz","calle falsa 45","699856652","ana@gmail.com","123456",false);
        controladorCliente.guardarCliente(cliente1);
        controladorCliente.guardarCliente(cliente);
        List<Cliente> clientes=controladorCliente.obtenerTodosLosClientes();
        clientes.forEach(System.out::println);
        System.out.println("--------DESPUES DE ELIMINAR---------\n");
        controladorCliente.borrarClienteDNI("96586326Q");
        controladorCliente.obtenerTodosLosClientes();
        List<Cliente> clientesDespues = controladorCliente.obtenerTodosLosClientes();
        assertEquals(1, clientesDespues.size());
        assertFalse(clientesDespues.stream().anyMatch(c -> c.getDni().equals("96586326Q")));
        List<Cliente> clientes2=controladorCliente.obtenerTodosLosClientes();
        clientes2.forEach(System.out::println);
    }

    @Test
    public void borrarClienteId() throws SQLException {
        Cliente cliente = new Cliente("45586326P", "Gustavo", "calle pimienta 5", "644856652", "gustavo@gmail.com", "abc12", true);
        controladorCliente.guardarCliente(cliente);
        controladorCliente.deleteId(1);
        Cliente borrado= (Cliente) controladorCliente.obtenerClienteId(1);
        assertNull(borrado);

    }
    @Test
    public void buscarClienteEmail() throws SQLException {
        Cliente cliente = new Cliente("45586326P", "Gustavo", "calle pimienta 5", "644856652", "gustavo@gmail.com", "abc12", true);
        controladorCliente.guardarCliente(cliente);
        controladorCliente.buscarClienteEmail("gustavo@gmail.com");
        //me aseguro que el cliente este en la lista
        List<Cliente> clientes = controladorCliente.obtenerTodosLosClientes();
        boolean encontrado = clientes.stream()
                .anyMatch(c -> c.getEmail().equals("gustavo@gmail.com"));
        assertTrue("El cliente con email gustavo@gmail.com no fue encontrado", encontrado);
    }

    @Test
    public void obtenerTodosLosClientes() throws SQLException {
        Cliente cliente1 = new Cliente("96586326Q", "Ana", "calle falsa 45", "699856652", "ana@gmail.com", "123456", false);
        Cliente cliente2 = new Cliente("45586326P", "Gustavo", "calle pimienta 5", "644856652", "gustavo@gmail.com", "abc12", true);
        controladorCliente.guardarCliente(cliente1);
        controladorCliente.guardarCliente(cliente2);
        List<Cliente> clientes= controladorCliente.obtenerTodosLosClientes();
        assertEquals(2,clientes.size());
        Cliente clienteRecuperado1 = clientes.getFirst();
        assertEquals("96586326Q", clienteRecuperado1.getDni());
        assertEquals("Ana", clienteRecuperado1.getNombre());
        Cliente clienteRecuperado2 = clientes.get(1);
        assertEquals("45586326P", clienteRecuperado2.getDni());
        assertEquals("Gustavo", clienteRecuperado2.getNombre());
        for(Cliente cliente : clientes){
            System.out.println(cliente);
        }
    }


}