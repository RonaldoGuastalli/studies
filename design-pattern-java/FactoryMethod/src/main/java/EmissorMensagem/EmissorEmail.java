package EmissorMensagem;

public class EmissorEmail implements Emissor{
    @Override
    public void envia(String mensagem) {
        System.out.println("Enviado por email a mensagem: ");
        System.out.println(mensagem);
    }
}
