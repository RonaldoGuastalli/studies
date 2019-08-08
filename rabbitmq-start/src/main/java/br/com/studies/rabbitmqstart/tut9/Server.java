package br.com.studies.rabbitmqstart.tut9;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Server {

    private static final String RPC_QUEUE = "rpc_queue";


    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Cria a fila fixa, caso não exista
            channel.queueDeclare(RPC_QUEUE, false, false, false, null);
            System.out.println(" [x] Awaiting RPC requests");

            // Response, objeto da mensagem
            Produto produto = Produto.builder()
                    .id(1)
                    .nome("Paracetamos")
                    .valor("R$ 100,00")
                    .build();

            // Callback das mensagens da fila, setando o correlationId que veio pela msg
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("[x] PDV Recebeu mensagem do servidor '" + message + "'");

                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, produto.toString().getBytes("UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };

            //consumindo as mensagens da fila
            channel.basicConsume(RPC_QUEUE, false, deliverCallback, consumerTag -> {});

        }
    }
}
