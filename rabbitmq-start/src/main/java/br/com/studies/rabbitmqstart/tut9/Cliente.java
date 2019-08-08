package br.com.studies.rabbitmqstart.tut9;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.UUID;

public class Cliente {

    private static final String RPC_QUEUE = "rpc_queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // Mensagem para a fila
        Produto produto = Produto.builder()
                .id(1)
                .nome("Paracetamol")
                .valor("R$ 100,00")
                .build();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Caso não existe cria a fila fixa
            channel.queueDeclare(RPC_QUEUE, false, false, false, null);
            channel.basicPublish("", RPC_QUEUE, null, produto.toString().getBytes());


            // Id gerado randomicamente
            final String corrId = UUID.randomUUID().toString();

            // Nome da fila, setar as propriedades (id e replicar para a fila)
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties properties = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            // Publicando na fila replica com REPLYTO, fila temporária
            channel.basicPublish("", replyQueueName, properties, produto.toString().getBytes("UTF-8"));

            // verificação da msg da fila replica
            String tag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)){
                    Produto.builder()
                            .id(1)
                            .nome("Paracetamol")
                            .valor("R$ 10,00")
                            .build();
                }

            }, consumerTag -> {});
        }
    }
}
