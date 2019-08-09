package br.com.studies.rabbitmqstart.tut9;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class ClienteUm implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    //inicialização
    public ClienteUm() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }


    public static void main(String[] args) {
        // Objeto no cliente
        String request = "mensagem no cliente";
        try {
            ClienteUm cliente = new ClienteUm();
            String response = cliente.call(request);
            System.out.println(" [.] Got '" + response + "'");

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        // Gerar id
        final String coorId = UUID.randomUUID().toString();
        // Nome da queue de reply
        String replyQueueName = channel.queueDeclare().getQueue();
        // Setando as props no servidor
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(coorId)
                .replyTo(replyQueueName)
                .build();
        // Publicar na fila reply
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        //
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        // Consumidor da reply, do servidor
        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(coorId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
