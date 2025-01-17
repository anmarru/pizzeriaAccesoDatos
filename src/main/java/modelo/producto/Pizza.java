package modelo.producto;
import jakarta.persistence.*;
import java.util.List;

import modelo.pedido.Ingrediente;

@Entity
@DiscriminatorValue("Pizza") // Valor para la columna tipo_producto
public class Pizza  extends Producto{
    @Enumerated(EnumType.STRING)
    private Size size;
   // @OneToMany(mappedBy = "ingrediente",fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Relación con Ingrediente, cada Pizza puede tener múltiples ingredientes
   // @JoinColumn(name = "pizza_id") // Relacionar la tabla Ingrediente con la tabla Pizza

    //@ElementCollection
   //@CollectionTable(name = "producto_ingrediente", joinColumns = @JoinColumn(name = "producto_id"))
   //@Column(name = "ingrediente") @AttributeOverride(name = "nombre", column = @Column(name = "nombre"))

   // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // @JoinTable(
    //        name = "producto_ingrediente",
      //      joinColumns = @JoinColumn(name = "producto_id"),
      //      inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
   // )
   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   @JoinTable(name = "producto_ingrediente",
           joinColumns = @JoinColumn(name = "producto_id"),
           inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredientes;

    public Pizza(String nombre, double precio, Size size,  List<Ingrediente> ingredientes) {
        super( nombre, precio);
        this.size = size;
        this.ingredientes=ingredientes;
    }

    public Pizza() {
    }

    public Pizza(Size size) {
        this.size = size;
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
        return super.toString()+" Pizza  [size=" + size + ", ingredientes=" + ingredientes + "]";
    }

    
    
    
    
}
