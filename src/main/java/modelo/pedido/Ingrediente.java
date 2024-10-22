package modelo.pedido;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Ingrediente {
    @CsvBindByName(column = "NOMBRE")
    private String nombre;
    @CsvBindAndSplitByName(column = "ALERGENOS", writeDelimiter = ",", elementType = String.class)
    private List<String> alergenos;
    @CsvBindByName(column = "ID")
    private int id;

    public Ingrediente() {
    }

    public Ingrediente(String nombre, List<String> alergenos,int id) {
        this.nombre = nombre;
        this.alergenos = alergenos;
        this.id = id;

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

    public long getId() {
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
