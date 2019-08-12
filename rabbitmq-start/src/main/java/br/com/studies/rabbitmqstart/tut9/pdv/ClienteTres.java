package br.com.studies.rabbitmqstart.tut9.pdv;

import br.com.studies.rabbitmqstart.tut9.mapper.Mapper;
import br.com.studies.rabbitmqstart.tut9.model.Produto;
import br.com.studies.rabbitmqstart.tut9.util.ConvertObjectToByteArray;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class ClienteTres implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private static final String RPC_QUEUE = "rpc_queue";

    //inicialização
    public ClienteTres() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        // Declara a fila fixa, caso ela não exista
        channel.queueDeclare(RPC_QUEUE, false, false, false, null);
    }


    public static void main(String[] args) {

        Produto produto1 = Mapper.mapperToProduto(
                1,
                "Fralda Geriátrica",
                23.99,
                "3",
                "Sapucaia");
        Produto produto2 = Mapper.mapperToProduto(
                2,
                "Ablok",
                2.25,
                "3",
                "Sapucaia");

        // Objeto no cliente
        List<Produto> request = new ArrayList<>();
        request.add(produto1);
        request.add(produto2);

        try {
            ClienteTres cliente = new ClienteTres();
            String response = cliente.call(request);
            System.out.println(" [x] Relatório da matriz '" + response + "'");

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(List<Produto> message) throws IOException, InterruptedException {
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
        byte[] messageBytes = ConvertObjectToByteArray.mapperToByte(message);
        channel.basicPublish("", RPC_QUEUE, props, messageBytes);

        //
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(10);

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
