package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import controlador.ControladorCilente;
import modelo.cliente.Cliente;

import modelo.pedido.*;
import modelo.producto.Bebida;
import modelo.producto.Pasta;
import modelo.producto.Pizza;
import modelo.producto.Size;
import utilidades.GestorDeArchivo;

import javax.xml.bind.JAXBException;

public class Pizzeria {



    public static void main(String[] args) {


        ControladorCilente controladorCilente= new ControladorCilente();

        //ingredientes
        Ingrediente ingredientePeperoni= new Ingrediente("peperoni",List.of("sulfito","lacteo"),1);
        Ingrediente ingredienteQueso= new Ingrediente("queso", List.of("lacteo"),2);
        Ingrediente ingredientePinia= new Ingrediente("piña",List.of(),3);
        Ingrediente ingredientePollo= new Ingrediente("pollo", List.of("aves"),4);
        Ingrediente ingredienteChampinion= new Ingrediente("champinion",List.of(),5);
        Ingrediente ingredienteAlbahaca= new Ingrediente("albahaca",List.of(),6);
        Ingrediente ingredientePata= new Ingrediente("pasta",List.of("gluten"),7);
        Ingrediente ingredienteBacon= new Ingrediente("bacon",List.of(),8);
        Ingrediente ingredienteArinaTrigo= new Ingrediente("arina de trigo",List.of("gluten"),9);
        Ingrediente ingredienteCarne = new Ingrediente( "Carne", List.of("Lactosa"),10);

        //lista ingredientes pasta
        List<Ingrediente> ingredientesPasta1= List.of(ingredientePata,ingredienteChampinion,ingredienteQueso);
        List<Ingrediente> ingredientesPasta2= List.of(ingredientePollo,ingredientePata,ingredienteQueso);
        List<Ingrediente> ingredientePasta3= List.of(ingredientePata,ingredientePollo,ingredienteQueso,ingredienteBacon);
        List<Ingrediente> ingredientesPasta4= List.of(ingredienteCarne,ingredientePata,ingredienteQueso);

        //lista ingredientes pizza
        List<Ingrediente> ingredientesPizza1= List.of(ingredientePeperoni,ingredienteQueso,ingredienteArinaTrigo);
        List<Ingrediente> ingredientesPizza2= List.of(ingredientePeperoni,ingredienteQueso,ingredienteArinaTrigo, ingredienteAlbahaca);
        List<Ingrediente> ingredientesPizza3= List.of(ingredienteBacon,ingredienteQueso,ingredienteArinaTrigo,ingredientePinia);



        //pastas
        Pasta pasta1=new Pasta(1,"pasta carbonara",10,ingredientesPasta1);
        Pasta pasta2= new Pasta(2,"pasta boloñesa",8.5,ingredientesPasta4);
        Pasta pasta3= new Pasta(3,"pasta Fettuccini",13,ingredientePasta3);

        //pizzas
        Pizza pizza1= new Pizza(4,"margarita",7.30, Size.MEDIANA,ingredientesPizza2);
        Pizza pizza2= new Pizza(5,"hawai",12, Size.GRANDE,ingredientesPizza3);
        Pizza pizza3= new Pizza(6,"margarita",7.30, Size.MEDIANA,ingredientesPizza2);

        //bebidas
        Bebida bebida1 = new Bebida(7, "cocacola", 1.80,Size.GRANDE);
        Bebida bebida2 = new Bebida(8, "crveza", 3.50,Size.MEDIANA);
        Bebida bebida3 = new Bebida(9, "agua", 0.90,Size.PEQUEÑA);


        //registro de clientes

        controladorCilente.registrarCliente(1, "54236985p", "Nico", "Calle 456", "456213698", "nico@gmail.com", "5555");
        controladorCilente.registrarCliente(2, "12345678a", "Luis", "Calle 123", "123456789", "luis@gmail.com", "1234");
        controladorCilente.registrarCliente(3,"23658964K","Ana","calle falsa 5","699853325","ana@gmail.com","123A");
        controladorCilente.registrarCliente(1, "54236985p", "maria", "Calle 456", "456213698", "maria@gmail.com", "5555");

        System.out.println("--------------------------------------------------------------------------------------------");
        //autenticar cliente
        controladorCilente.autenticarCliente("luis@gmail.com","1234");
        controladorCilente.autenticarCliente("ana@gmail.com","123A");
        controladorCilente.autenticarCliente("luis@gmail.com","123456");//contraseña erronea

        System.out.println("--------------------------------------------------------------------------------------------");

        //lineas de pedido
        LineaPedido lineaPedido1=new LineaPedido(1,3,pasta2);
        LineaPedido lineaPedido2=new LineaPedido(2,2,pizza1);
        LineaPedido lineaPedido3=new LineaPedido(3,2,bebida1);
        LineaPedido lineaPedido4=new LineaPedido(4,1,bebida3);

        //añado lineas al pedido
        controladorCilente.agregarLieaPedido(lineaPedido1);
        controladorCilente.agregarLieaPedido(lineaPedido3);
        controladorCilente.agregarLieaPedido(lineaPedido2);
        controladorCilente.agregarLieaPedido(lineaPedido4);
        //muestro informacion
        System.out.println(controladorCilente.getControladorPedido().getPedidoactual());

        //metodos de pago
        PagarEfectivo pagoEfectivo= new PagarEfectivo();
        PagarTarjeta pagarTarjeta= new PagarTarjeta();

        //finalizo pedido
        controladorCilente.finalizarPedido(pagarTarjeta);
        //pedido estado finalizado
        System.out.println(controladorCilente.getControladorPedido().getPedidoactual());

        //cancelo pedido para reiniciar un nuevo  pedido
        controladorCilente.cancelarPedido();

        //añado listas al pedido
        controladorCilente.agregarLieaPedido(lineaPedido1);
        System.out.println(controladorCilente.getControladorPedido().getPedidoactual());
        controladorCilente.finalizarPedido(pagoEfectivo);
        controladorCilente.recibirPedido();
        System.out.println(controladorCilente.getControladorPedido().getPedidoactual());

        Cliente cliente1= new Cliente(5,"8569426","stephania","calle y","965368526","stephania@gmail.com","456987",true);


        System.out.println("-------------------------------ARCHIVOS-----------------------------------------------------------\n");
        //-------------------PRACTICA ARCHIVOS--------------------------------------------------------------

        //FUNCION LEER ARCHIVO TXT
        try {
                List<Cliente> administradores=controladorCilente.leerArchivoTXT();
                administradores.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        List<Cliente> listaClientes= new ArrayList<>();
        listaClientes.add(new Cliente(1,"5235474Q","nico","calle x 12","652358896","nico@gmail.com","14563A"));
        listaClientes.add(new Cliente(2, "22203344F", "María", "C/Sol 5", "678954321", "m.gonzalez@correo.com", "23456B"));
        listaClientes.add(new Cliente(3, "33301234A", "Carlos", "Av. Libertad 3", "690123456", "c.lopez@correo.com", "34567C"));

        //FUNCION EXPORTAR CLIENTESXML
        try {
            controladorCilente.exportarArchivoClientesXML(listaClientes);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }


        //FUNCION IMPORTAR CLIENTEXML

        //creo mi lista para almacenar los obj importados del xml
        List<Cliente>  listaClientesxml;
        try {
             listaClientesxml = controladorCilente.importarArchivoClientesXML();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        if(listaClientes !=null){
            listaClientesxml.forEach(System.out::println);
        }else {
            System.out.println("no se pudieron importar los clientes ");
        }


        //FUNCION EXPORTAR ARCHIVO CSV
        List<Ingrediente> lista_ingredientes= new ArrayList<>();
        lista_ingredientes.add(new Ingrediente("peperoni",List.of("sulfito","lacteo"),1));
        lista_ingredientes.add(new Ingrediente("queso",List.of("lacteo"),2));
        lista_ingredientes.add(new Ingrediente("piña",List.of("bromelia", "histamina"),3));
        lista_ingredientes.add(new Ingrediente("pechuga de pollo",List.of("aves"),4));
        lista_ingredientes.add(new Ingrediente("champiñones",List.of(),5));

        try {
            controladorCilente.exportarArchivoIngredientesCSV(lista_ingredientes);
        } catch (FileNotFoundException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }

        //FUNCION IMPORTAR ARCHIVO CSV
        try {
            controladorCilente.importarArchivoIngredienteCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         lista_ingredientes.forEach(System.out::println);



    }
 
}
