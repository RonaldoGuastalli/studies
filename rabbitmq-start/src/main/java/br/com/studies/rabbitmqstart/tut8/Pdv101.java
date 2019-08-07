package br.com.studies.rabbitmqstart.tut8;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class Pdv101 implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "teste_pdv";

    public Pdv101() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
        try {
            Pdv101 pdvRpc = new Pdv101();
            for (int i = 0; i < 2; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting pdvRpc(" + i_str + ")");
                String response = pdvRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    //call
    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        Produto produto1 = Mapper.mapperToProduto(
                "1",
                "Dipirona",
                "12,50",
                "101",
                "Canoas"
        );
        byte[] produtoBytes = ConvertObjectToByteArray.mapperToByte(produto1);

        //fila
        String queueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(queueName)
                .build();

        //publica
        channel.basicPublish("", requestQueueName, props, produtoBytes);

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        //consumidor, com um consumerTag gerado.
        String ctag = channel.basicConsume(queueName, false, (consumerTag, delivery) -> {}, consumerTag -> {});

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
