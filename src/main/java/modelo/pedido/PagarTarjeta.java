package modelo.pedido;

public class PagarTarjeta implements Pagable{

    @Override
    public void pagar(double cantidad) {
        System.out.println(cantidad+ " PAGO REALIZADO CON TARJETA...\n");
        
    }
    
}
