package modelo.producto;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Bebida") // Valor para la columna tipo_producto
public class Bebida extends Producto{
    @Enumerated(EnumType.STRING)
    public  Size size;

    public Bebida(String nombre, double precio, Size size) {
        super(nombre, precio);
        this.size = size;
    }

    public Bebida(){
    }
    public Size getSize() {
        return size;
    }


    @Override
    public String toString() {
        return super.toString()+"Bebida"+super.toString()+" [size=" + size + "]";
    }








}
