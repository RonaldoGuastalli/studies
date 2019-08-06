package br.com.studies.rabbitmqstart.tut7;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "fila-objeto";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Pessoa pessoa = new Pessoa(1, "Paulo", "Silva");
            byte[] pessoaBytes = ConvertObjectToByteArray.mapperToByte(pessoa);


            channel.basicPublish("", QUEUE_NAME, null, pessoaBytes);
            System.out.println(" [x] Sent '" + pessoaBytes + "'");
        }
    }
}
