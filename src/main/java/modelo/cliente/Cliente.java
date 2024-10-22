package modelo.cliente;

import java.util.ArrayList;
import java.util.List;

import modelo.pedido.Pagable;
import modelo.pedido.Pedido;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente implements Pagable {
    @XmlAttribute(name = "id")
    private long id;
    @XmlElement(name = "dni")
    private String dni;
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "direccion")
    private String direccion;
    @XmlElement(name = "telefono")
    private String telefono;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "password")
    private String password;
    @XmlTransient
    private List<Pedido> pedidos;
    @XmlElement(name = "esAdministrador")
    private boolean esAdministrador=false;

    public Cliente() {
    }

    public Cliente( long id,String dni, String nombre, String direccion, String telefono, String email,
                   String password, boolean esAdministrador) {
        this.id=id;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.pedidos = new ArrayList<>();
        this.esAdministrador=esAdministrador;
    }



    @Override
    public void pagar(double cantidad) {
        System.out.println("HACER PAGO");
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono="
                + telefono + ", email=" + email + ", password=" + password + ", pedidos=" + pedidos  +" ,esAdministrador "+ esAdministrador+ "]\n";
    }

    public void agregarPedidos(Pedido pedido){
        pedidos.add(pedido);
    }

    public void setId(long id) {
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






    
}
