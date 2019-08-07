package br.com.studies.rabbitmqstart.tut7;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "fila-objeto";
    private final static String QUEUE_NAME_CALLBACK = "fila-callback-objeto";
    private final static String EXCHANGE_NAME_BEFORE = "exchange-before-objeto";
    private final static String EXCHANGE_NAME_AFTER = "exchange-after-objeto";
    private final static String ROUTING_KEY = "routing-name-objeto";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();

             //channel
             Channel channel = connection.createChannel()) {

            //exchange
            channel.exchangeDeclare(EXCHANGE_NAME_BEFORE, "direct", true);
//            channel.exchangeDeclare(EXCHANGE_NAME_AFTER, "direct", true);

            //queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("criou a queue: " + QUEUE_NAME);

            //objeto para enviar
            Pessoa pessoa = new Pessoa(1, "Paulo", "Silva");
            byte[] pessoaBytes = ConvertObjectToByteArray.mapperToByte(pessoa);

            //envio para queue
            channel.basicPublish("", QUEUE_NAME, null, pessoaBytes);
            System.out.println(" [x] Sent '" + pessoaBytes + "'");

            //enviar para exchenge e queue''
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME_BEFORE, ROUTING_KEY);

//            //callback
//            String callbackQueueName = channel.queueDeclare().getQueue();
//            System.out.println("callbackQueueName é " + callbackQueueName);
//            AMQP.BasicProperties props = new AMQP.BasicProperties
//                    .Builder()
//                    .replyTo(callbackQueueName)
//                    .build();
//            channel.basicPublish("", ROUTING_KEY, props, pessoaBytes);
        }
    }
}
