package com.baihailang.provider.mq05topic;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者1
public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("que-topic1", true, false, false, null);
        channel.exchangeDeclare("exchange-topic", BuiltinExchangeType.TOPIC);
        channel.queueBind("que-topic1", "exchange-topic", "#.topic1.#");
        channel.basicConsume("que-topic1", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者1消费的消息===========>" + new java.lang.String(body));
                Object h1 = properties.getHeaders().get("h1");
                System.out.println(h1);
            }
        });
    }
}
