package com.baihailang.provider.mq04router;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 消费者2
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("que-router2", true, false, false, null);
        channel.exchangeDeclare("exchange-direct", BuiltinExchangeType.DIRECT);
        channel.queueBind("que-router2", "exchange-direct", "router2");
        channel.basicConsume("que-router2",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2");
                System.out.println("收到的消息为==========>" + new String(body));
            }
        });

    }
}
