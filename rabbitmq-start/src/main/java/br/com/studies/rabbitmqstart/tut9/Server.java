package br.com.studies.rabbitmqstart.tut9;

import br.com.studies.rabbitmqstart.tut9.model.Produto;
import br.com.studies.rabbitmqstart.tut9.util.RelatorioVenda;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final String RPC_QUEUE = "rpc_queue";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

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
                ObjectMapper objectMapper = new ObjectMapper();
                List<Produto> produtos = objectMapper.readValue(delivery.getBody(), new TypeReference<List<Produto>>(){});
                System.out.println(" [x] server produtos >>>> " + produtos);

                Double valorVenda = RelatorioVenda.totalDeVendas(produtos);
                List<String> nomeDosProdutos = RelatorioVenda.produtoDaVenda(produtos);
                RelatorioVenda relatorio = RelatorioVenda.relatorio(nomeDosProdutos, valorVenda);

                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, relatorio.toString().getBytes("UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                //
                synchronized (monitor) {
                    monitor.notify();
                }
            };

            //
            channel.basicConsume(RPC_QUEUE, false, deliverCallback, consumerTag -> {
            });
            //
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

    }
}
