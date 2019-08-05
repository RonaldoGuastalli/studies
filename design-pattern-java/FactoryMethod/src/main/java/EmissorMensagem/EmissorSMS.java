package EmissorMensagem;

public class EmissorSMS implements Emissor {

    @Override
    public void envia(String mensagem) {
        System.out.println("Enviado por SMS a mensagem: ");
        System.out.println(mensagem);
    }
}
