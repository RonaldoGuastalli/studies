package br.com.studies.rabbitmqstart.tut9;

import com.rabbitmq.client.*;

public class Server {

    private static final String RPC_QUEUE = "rpc_queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            // Declara a fila, caso ela não exista
            channel.queueDeclare(RPC_QUEUE, false, false, false, null);

            System.out.println(" [x] Awaiting RPC requests");

            // Objeto trabalhado no servido
            String response = "mensagem modificada no servido";

            Object monitor = new Object();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // Parametros do server para o Id
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();
                // Publicar na fila retly

                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                //
                synchronized (monitor) {
                    monitor.notify();
                }
            };

            //
            channel.basicConsume(RPC_QUEUE, false, deliverCallback, consumerTag -> {});
            //
            while(true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }


        }

    }
}
