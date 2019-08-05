package EmissorMensagem;

public class EmissorCreatorTeste {

    public static void main(String[] args) {

        //factory
        EmissorCreator creator = new EmissorCreator();

        Emissor emissorSMS = creator.create(EmissorCreator.SMS);
        emissorSMS.envia("Java Design Pattern");

        Emissor emissorEmail = creator.create(EmissorCreator.EMAIL);
        emissorEmail.envia("Java Design Pattern");

        Emissor emissorJMS = creator.create(EmissorCreator.JSM);
        emissorJMS.envia("Java Design Pattern");
    }
}
