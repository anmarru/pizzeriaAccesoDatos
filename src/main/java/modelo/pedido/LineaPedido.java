package modelo.pedido;

import jakarta.persistence.Entity;
import modelo.producto.Producto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int cantidad;
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos-a-uno con Producto
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    @Column(nullable = false)
    private int pedido_id;


    public LineaPedido(int id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;

    }

    public LineaPedido(int id, int cantidad, Producto producto, int pedido_id) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido_id = pedido_id;
    }

    public LineaPedido(LineaPedido nuevalineaPedido) {
        this(nuevalineaPedido.id, nuevalineaPedido.cantidad, nuevalineaPedido.producto);
    }

    public LineaPedido() {
    }

    @Override
    public String toString() {
        return "LineaPedido [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + getPrecio() + "]";
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void aniadirCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public Producto getProducto() {
        return producto;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return cantidad * producto.getPrecio();
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaPedido that = (LineaPedido) o;
        return id == that.id && cantidad == that.cantidad && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, producto);
    }
}
