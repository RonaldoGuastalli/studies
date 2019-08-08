package br.com.studies.rabbitmqstart.tut8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class MatrizServer {

    private static final String RPC_QUEUE_NAME = "server-queue";
    private static final String PDV_QUEUE_100 = "pdv-100";
    private static final String PDV_QUEUE_101 = "pdv-101";

    public static void main(String[] argv) throws Exception {
        RabbitConnection channel = new RabbitConnection();

        channel.channelReturn().queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        System.out.println("...Awaiting RPC requests");

        // ouvindo as mensagens vidas dos pdvs
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] SERVIDOR recebeu a '" + message + "'");
        };
        channel.channelReturn().basicConsume(RPC_QUEUE_NAME, false, deliverCallback, consumerTag -> {

        });

        //enviar para fila temporária do PDV
        enviarMensagemParaPdvs(PDV_QUEUE_100, "OBJETO 100");
        enviarMensagemParaPdvs(PDV_QUEUE_101, "OBJETO 101");



    }

    public static void enviarMensagemParaPdvs(String nomeDaFila, Object o) throws Exception {
        RabbitConnection channel = new RabbitConnection();
        System.out.println("[.] Mensagem enviada " + o.toString() + " para a fila " + nomeDaFila);
        channel.channelReturn().queueDeclare(nomeDaFila, false, false, true, null);
        channel.channelReturn().basicPublish("", nomeDaFila,null, o.toString().getBytes());
    }


}
