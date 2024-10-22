package modelo.producto;

import java.util.List;

import modelo.pedido.Ingrediente;

public class Pasta extends Producto {

    protected List <Ingrediente> ingredientes;

    public Pasta(int id, String nombre, double precio, List<Ingrediente> ingredientes) {
        super(id, nombre, precio);
        this.ingredientes=ingredientes;
        
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Pasta [ingredientes=" + ingredientes + "]";
    }
    
    
    
}
