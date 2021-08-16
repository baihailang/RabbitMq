package com.baihailang.provider.mq04router;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import netscape.security.UserTarget;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 生产者
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("exchange-direct", "router1", null, "123router1".getBytes());
            channel.basicPublish("exchange-direct", "router2", null, "456router2".getBytes());
        }
        connection.close();
    }
}
