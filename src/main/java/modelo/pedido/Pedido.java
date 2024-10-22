package modelo.pedido;

import modelo.cliente.Cliente;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {

    private List<LineaPedido> lineasPedido;
    private final long id;
    private final Date fecha;
    private float precioTotal;
    private EstadoPedido estado;
    private final Cliente cliente;


    public Pedido( long id, Cliente cliente) {
        this.id=id;
        this.fecha = new Date();
        this.precioTotal =0;
        this.lineasPedido= new ArrayList<>();
        this.estado= EstadoPedido.PENDIENTE;
        this.cliente=cliente;
    }

    public Pedido(Pedido pedido){
        this(pedido.id,pedido.cliente);
    }
    
    
    @Override
    public String toString() {
        String info= "Pedido [id=" + id + ", fecha=" + fecha + ", precioTotal=" + precioTotal + ", estado=" + estado + "]\n";
        for(LineaPedido l: lineasPedido){
            info+=l.toString()+"\n";
        }
        return info;
    }


    public void agregarLineaPedido(LineaPedido nuevalineaPedido){
        boolean lineaExistente= false;

        for(LineaPedido linea:lineasPedido){

            if(linea.getId()==nuevalineaPedido.getId()){
                linea.aniadirCantidad(nuevalineaPedido.getCantidad());
                lineaExistente=true;
                break;
            }
        }
        if(!lineaExistente){
            lineasPedido.add(new LineaPedido(nuevalineaPedido));
        }

        precioTotal+=nuevalineaPedido.getCantidad()*nuevalineaPedido.getPrecio();
    
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public long getId() {
        return id;
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


    

    
}
