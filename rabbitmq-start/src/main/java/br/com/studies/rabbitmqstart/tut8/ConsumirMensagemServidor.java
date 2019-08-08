package br.com.studies.rabbitmqstart.tut8;

import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ConsumirMensagemServidor {

    public static void consumir(String nomeDaFila) throws Exception {
        RabbitConnection channel = new RabbitConnection();
        ouvindoFila(channel, nomeDaFila);
    }
    static void ouvindoFila(RabbitConnection channel, String pdvQueue) throws IOException, TimeoutException {
        channel.channelReturn().queueDeclare(pdvQueue, false, false, true, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] PDV Recebeu mensagem do servidor '" + message + "'");
        };
        channel.channelReturn().basicConsume(pdvQueue, false, deliverCallback, consumerTag -> {

        });
    }
}
