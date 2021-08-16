package com.baihailang.provider.mq04router;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者1
public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange-direct", BuiltinExchangeType.DIRECT);
        channel.queueDeclare("que-router1", true, false, false, null);
        channel.queueBind("que-router1", "exchange-direct", "router1");
        channel.basicConsume("que-router1",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者1");
                System.out.println("body=============>" + new String(body));
            }
        });
    }
}
