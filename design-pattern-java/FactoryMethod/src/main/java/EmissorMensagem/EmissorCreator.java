package emissorMensagem;

public class EmissorCreator {

    public static final int SMS = 0;
    public static final int EMAIL = 1;
    public static final int JSM = 2;

    //factory method
    public Emissor create(int tipoDeEmissor){
        if(tipoDeEmissor == EmissorCreator.SMS){
            return new EmissorSMS();
        }else if(tipoDeEmissor == EmissorCreator.EMAIL){
            return new EmissorEmail();
        }else if(tipoDeEmissor == EmissorCreator.JSM){
            return new EmissorJMS();
        }else{
            throw new IllegalArgumentException("Tipo de emissor não suportado");
        }
    }
}
