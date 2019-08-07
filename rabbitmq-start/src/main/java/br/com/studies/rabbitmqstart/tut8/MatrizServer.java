package br.com.studies.rabbitmqstart.tut8;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class MatrizServer {

    private static final String RPC_QUEUE_NAME = "server-queue";

    public static void main(String[] argv) throws Exception {
        RabbitConnection channel = new RabbitConnection();

        channel.channelReturn().queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        System.out.println(" [x] Awaiting RPC requests");


        //Interface de retorno de chamada a ser notificada quando uma mensagem e entregue
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties
//                        .Builder()
//                        .correlationId(delivery.getProperties().getCorrelationId())
//                        .build();


            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] PDV Received '" + message + "'");
        };
        channel.channelReturn().basicConsume(RPC_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });


    }


}
