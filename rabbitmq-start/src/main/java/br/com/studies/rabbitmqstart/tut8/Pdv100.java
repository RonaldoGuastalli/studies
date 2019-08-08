package br.com.studies.rabbitmqstart.tut8;

public class Pdv100 {

    private static final String PDV_QUEUE = "pdv-100";
    private static String sendQueueName = "server-queue";

    public static void main(String[] argv) throws Exception {
        RabbitConnection channel = new RabbitConnection();
        enviarParaFila(sendQueueName, Mapper.mapperToProduto(
                "1",
                "Paracetamol",
                "R$ 12,50",
                "100",
                "Canoas"
        ));

        ConsumirMensagemServidor.consumir(PDV_QUEUE);
    }

    public static void enviarParaFila(String nomeDaFila, Object o) throws Exception {
        RabbitConnection channel = new RabbitConnection();
        System.out.println("[.] Mensagem enviada " + o.toString() + " para fila " + nomeDaFila);
        channel.channelReturn().queueDeclare(nomeDaFila, false, false, false, null);
        channel.channelReturn().basicPublish("", nomeDaFila, null, o.toString().getBytes());
    }

}
