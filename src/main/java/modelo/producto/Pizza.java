package modelo.producto;

import java.util.List;

import modelo.pedido.Ingrediente;

public class Pizza  extends Producto{
    
    private Size size;

    private List<Ingrediente> ingredientes;

    public Pizza(int id, String nombre, double precio, Size size,  List<Ingrediente> ingredientes) {
        super(id, nombre, precio);
        this.size = size;
        this.ingredientes=ingredientes;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Pizza [size=" + size + ", ingredientes=" + ingredientes + "]";
    }

    
    
    
    
}
