package br.com.studies.rabbitmqstart.tut8;

public class Pdv100 {

    private static String requestQueueName = "pdv_send";
    private static String sendQueueName = "server-queue";

    public static void main(String[] argv) throws Exception {
        enviarParaFila(sendQueueName, Mapper.mapperToProduto(
                "1",
                "Paracetamol",
                "12,50",
                "100",
                "Porto Alegre"
        ));


    }

    public static void enviarParaFila(String nomeDaFila, Object o) throws Exception {
        RabbitConnection channel = new RabbitConnection();
        System.out.println("Mensagem enviada " + o.toString() + " para fila " + nomeDaFila);

        channel.channelReturn().queueDeclare(nomeDaFila, false, false, false, null);
        channel.channelReturn().basicPublish("", nomeDaFila, null, o.toString().getBytes());
    }

}
