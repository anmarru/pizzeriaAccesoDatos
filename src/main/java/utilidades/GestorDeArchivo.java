package utilidades;
import com.opencsv.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import modelo.cliente.Cliente;
import modelo.cliente.Clientes;
import modelo.pedido.Ingrediente;

import javax.xml.bind.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;

public class GestorDeArchivo {

    public  static List<Cliente> leerCliente(String nombreArchivo) throws IOException {

        List<Cliente> listaCliente= new ArrayList<>();
        try(BufferedReader reader= new BufferedReader(new FileReader(nombreArchivo))){
            String lineas;
            while ((lineas= reader.readLine()) !=null){
                String[] campos= lineas.split("[;,|]");
                //quito espacios dentro de cada campo
                for(int i=0; i <campos.length; i++){
                    campos[i]= campos[i].trim();//elimina todos los espacios q tenga al principio y al final
                }

                long id=Long.parseLong(campos[0]) ;
                String dni=campos[1];
                String nombre=campos[2];
                String direccion= campos[3];
                String telefono= campos[4];
                String  email= campos [5];
                String password= campos[6];
                boolean esAdmin= Boolean.parseBoolean(campos[7]);

                Cliente cliente= new Cliente(id, dni, nombre,direccion,telefono,email, password,esAdmin);
                listaCliente.add(cliente);
            }
        }

        return  listaCliente;
    }

    public static void exportarClienteAxml(List<Cliente> listaClientes, String nombreArchivo) throws JAXBException {
            //contiene mi lista de clientes
            Clientes cliente=new Clientes(listaClientes);

            JAXBContext context=JAXBContext.newInstance(Clientes.class);
            Marshaller marshaller=context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(cliente,new File(nombreArchivo));
            System.out.println("exportado archivoXML...\n");
    }

    public static List<Cliente> importarClientesDesdeArchivoXML(String ruraArchivo) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Clientes.class);

        Unmarshaller unmarshaller= context.createUnmarshaller();
        //leo el archivo y lo convierto a obCliente
        File archivoXml=new File(ruraArchivo);
        Clientes clientes= (Clientes)unmarshaller.unmarshal(archivoXml);

        //devuelvo la lista
        return  clientes.getClientes();
    }

    public static void exportarIngredienteCSV(List<Ingrediente> listaDeIngredientes) throws FileNotFoundException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (PrintWriter pw = new PrintWriter("Ingredientes.csv")) {
            StatefulBeanToCsv<Ingrediente> beanToCsv = new StatefulBeanToCsvBuilder<Ingrediente>(pw).withSeparator(';').build();
            beanToCsv.write(listaDeIngredientes);
            System.out.println("exportado archivoCSV...\n");
        }
    }

    public static List<Ingrediente> importarIngredientesDesdeCSV() throws IOException {
        List<Ingrediente> ingredientes;

        try (FileReader fileReader = new FileReader("ingredientes.csv")) {
            // Se crea un csvToBean de clase ingrediente
            CsvToBean<Ingrediente> csvToBean = new
                    CsvToBeanBuilder<Ingrediente>(fileReader)
                    .withType(Ingrediente.class)
                    .build();
            // Parsea el fichero CSV en una lista de ingrdientes
            ingredientes = csvToBean.parse();
        }

        return ingredientes;
    }
}
