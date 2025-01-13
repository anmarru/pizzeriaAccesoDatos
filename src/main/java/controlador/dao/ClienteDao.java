package controlador.dao;

import modelo.cliente.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {

    void save(Cliente cliente) throws SQLException;

    void delete(String dni) throws SQLException;

    void deleteId(int id) throws SQLException;

    void update(Cliente cliente) throws SQLException;

    Cliente findByEmail(String email);

     List<Cliente> findAll();

     Cliente obtenerClientePorId(int clienteId);

     Cliente login(String email, String password);

}
