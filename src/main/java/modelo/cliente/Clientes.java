package modelo.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name= "clientes")
@XmlAccessorType(XmlAccessType.FIELD)

public class Clientes {
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;

    public Clientes(){}


    public Clientes(List<Cliente> clientes){
        this.clientes= clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
