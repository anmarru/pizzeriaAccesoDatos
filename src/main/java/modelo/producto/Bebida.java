package modelo.producto;

public class Bebida extends Producto{
    
    
    public final Size size;
    
    public Bebida(int id, String nombre, double precio, Size size) {
        super(id, nombre, precio);
        this.size = size;
        
    }

    public Size getsize() {
        return size;
    }


    @Override
    public String toString() {
        return "Bebida [size=" + size + "]";
    }

    

    
    

    
    
}
