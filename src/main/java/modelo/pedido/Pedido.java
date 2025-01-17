package modelo.pedido;

import jakarta.persistence.Entity;
import modelo.cliente.Cliente;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos") //nombre de la tabla
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genero id automatico
    private int id;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;
    @Column(name = "precio_total", nullable = false)
    private float precioTotal;
    @Enumerated(EnumType.STRING) //mapea un enum como String en la BD
    @Column(nullable = false)
    private EstadoPedido estado;
    @ManyToOne(fetch = FetchType.LAZY) //relacion muchos-a-uno con Cliente
    @JoinColumn(name = "cliente_id", nullable = false)
    private  Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id") // Clave foránea en la tabla LineaPedido
    private List<LineaPedido> lineasPedido;


    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.fecha = new Date();
        this.precioTotal = 0;
        this.lineasPedido = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE;
        this.cliente = cliente;
    }

    public Pedido() {

    }

/*public Pedido(Pedido pedido) {
        this(pedido.id, pedido.cliente);
    }*/


    @Override
    public String toString() {
        String info = "Pedido [id=" + id + ", fecha=" + fecha + ", precioTotal=" + precioTotal + ", estado=" + estado + "]\n";
        for (LineaPedido l : lineasPedido) {
            info += l.toString() + "\n";
        }
        return info;
    }


    public void agregarLineaPedido(LineaPedido nuevalineaPedido) {
        boolean lineaExistente = false;

        for (LineaPedido linea : lineasPedido) {

            if (linea.getId() == nuevalineaPedido.getId()) {
                linea.aniadirCantidad(nuevalineaPedido.getCantidad());
                lineaExistente = true;
                break;
            }
        }
        if (!lineaExistente) {
           lineasPedido.add(new LineaPedido(nuevalineaPedido));

        }

        precioTotal += nuevalineaPedido.getCantidad() * nuevalineaPedido.getPrecio();

    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public int getId() {
        return id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }


    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setId(int id) {
        this.id = id;
    }
    /* public void setFecha(java.sql.Date fecha) {
        this.fecha = new java.util.Date(fecha.getTime());
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id && Float.compare(precioTotal, pedido.precioTotal) == 0 && Objects.equals(fecha, pedido.fecha) && estado == pedido.estado && Objects.equals(cliente, pedido.cliente) && Objects.equals(lineasPedido, pedido.lineasPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, precioTotal, estado, cliente, lineasPedido);
    }
}
