package com.baihailang.provider.mq03publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory =new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish("exchange-fanout", "", null, "aaaaa".getBytes());
        }
        System.out.println("发送成功！");
        connection.close();
    }
}
