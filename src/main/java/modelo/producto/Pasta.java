package modelo.producto;

import java.util.List;
import jakarta.persistence.*;
import modelo.pedido.Ingrediente;

@Entity
@DiscriminatorValue("Pasta")  // Indica que esta clase es un tipo de Producto "Pasta
public class Pasta extends Producto {

   // @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // Relación OneToMany con carga perezosa y cascada
   // @JoinColumn(name = "pasta_id") // Relacionar Ingrediente con Pasta a través de la columna "pasta_id"
   @ElementCollection
   @CollectionTable(name = "producto_ingrediente", joinColumns = @JoinColumn(name = "producto_id"))
   @Column(name = "ingrediente")
   @AttributeOverride(name = "nombre", column = @Column(name = "nombre"))
    protected List <Ingrediente> ingredientes;

    public Pasta(String nombre, double precio, List<Ingrediente> ingredientes) {
        super(0, nombre, precio);
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
        return super.toString()+"Pasta" +"[ingredientes=" + ingredientes + "]";
    }
    
    
    
}
