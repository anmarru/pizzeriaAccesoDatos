package modelo.producto;

import java.util.Objects;

public abstract class Producto {
    
    
    protected final int id;
    protected String nombre;
    protected double precio;


    public Producto(int id, String nombre, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.precio = precio;

    }

    
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + super.toString()+ "]";
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
