package modelo.pedido;
import modelo.producto.Producto;

public class LineaPedido {
    private int id;
    private int cantidad;
    private Producto producto;
    

    public LineaPedido(int id,int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto=producto;
        
    }

    public LineaPedido(LineaPedido nuevalineaPedido) {
        this(nuevalineaPedido.id, nuevalineaPedido.cantidad, nuevalineaPedido.producto);
    }


    @Override
    public String toString() {
        return "LineaPedido [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + getPrecio()+ "]";
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

    public void aniadirCantidad(int cantidad){
        this.cantidad+=cantidad;
    }

    public Producto getProducto() {
        return producto;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio(){
        return cantidad * producto.getPrecio();
    }
    
    

    
    

    
}
