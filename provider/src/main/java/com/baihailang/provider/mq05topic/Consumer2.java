package com.baihailang.provider.mq05topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者2
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("que-topic2", true, false, false, null);
        channel.exchangeDeclare("exchange-topic", BuiltinExchangeType.TOPIC);
        channel.queueBind("que-topic2", "exchange-topic", "#.topic2.#");
        channel.basicConsume("que-topic2", new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2消费的消息================》" + new String(body));
                System.out.println(properties.getHeaders().get("h2"));
            }
        });
    }
}
