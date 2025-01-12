package modelo.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import modelo.pedido.Pagable;
import modelo.pedido.Pedido;

import javax.xml.bind.annotation.*;

@Entity
@Table(name = "clientes")//nombre de la tabla de la bd
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente implements Pagable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//genero la clave primaria automatica
    @XmlAttribute(name = "id")
    private int id;
    @Column(nullable = false, unique = true) //no puede ser nulo y debe ser unico
    private String dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false, unique = true) //no puede ser nulo y debe ser unico
    private String email;
    @Column(nullable = false)
    private String password;
    //relacion con Pedido
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //etiqueta para que no lo tenga en cuenta
    @XmlTransient
    private List<Pedido> pedidos;
    private boolean esAdministrador;

    public Cliente() {
    }

    public Cliente(int id, String dni, String nombre, String direccion, String telefono, String email,
                   String password) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.pedidos = new ArrayList<>();
        this.esAdministrador = false;
    }

    public Cliente( String dni, String nombre, String direccion, String telefono, String email, String password, boolean esAdministrador) {
        this.dni=dni;
        this.nombre= nombre;
        this.direccion= direccion;
        this.telefono=telefono;
        this.email= email;
        this.password=password;

        this.esAdministrador = esAdministrador;
    }

    @Override
    public void pagar(double cantidad) {
        System.out.println("HACER PAGO");
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono="
                + telefono + ", email=" + email + ", password=" + password + " ,esAdministrador " + esAdministrador + "]\n";
    }

    public void agregarPedidos(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id && esAdministrador == cliente.esAdministrador && Objects.equals(dni, cliente.dni) && Objects.equals(nombre, cliente.nombre) && Objects.equals(direccion, cliente.direccion) && Objects.equals(telefono, cliente.telefono) && Objects.equals(email, cliente.email) && Objects.equals(password, cliente.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, nombre, direccion, telefono, email, password, esAdministrador);
    }
}
