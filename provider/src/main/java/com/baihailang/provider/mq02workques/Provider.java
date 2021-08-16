package com.baihailang.provider.mq02workques;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory= new ConnectionFactory();
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.Queue.DeclareOk workques = channel.queueDeclare("workques", true, false, false, null);
        for (int i = 0; i <5 ; i++) {
            channel.basicPublish("","workques",null,("aaaa"+i).getBytes());
        }
        channel.close();
        connection.close();
    }
}
