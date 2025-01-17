package modelo.pedido;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name= "ingrediente")
public class Ingrediente {
    @CsvBindByName(column = "NOMBRE")
    @Column(unique = true,nullable = false)//solo hay un nombre de ingrediente
    private String nombre;
    //para que lea la lista de la columna de alergenos separadas por comas de la clase String
    @CsvBindAndSplitByName(column = "ALERGENOS", writeDelimiter = ",", elementType = String.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingrediente_alergenos", joinColumns = @JoinColumn(name = "ingrediente_id"))
    @Column(name = "alergeno")
    private List<String> alergenos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "ID")
    private int id;

    public Ingrediente() {
    }

    public Ingrediente(String nombre, List<String> alergenos) {
        this.nombre = nombre;
        this.alergenos = alergenos;
    }

    public Ingrediente(int id, String nombre) {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public List<String> getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List<String> alergenos) {
        this.alergenos = alergenos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", alergenos=" + alergenos +
                '}';
    }


}
