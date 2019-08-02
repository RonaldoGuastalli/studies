package emissorMensagem;

public class EmissorTeste {

    public static void main(String[] args) {
        Emissor emissorSMS = new EmissorSMS();
        emissorSMS.envia("Java Design Pattern");

        Emissor emissorEmail = new EmissorEmail();
        emissorEmail.envia("Java Design Pattern");

        Emissor emissorJMS = new EmissorJMS();
        emissorJMS.envia("Java Design Pattern");
    }
}
