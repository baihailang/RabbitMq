package com.baihailang.provider.mq05topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//生产者
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i <10 ; i++) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("h1", "你好");
            map.put("h2", "hello");
            AMQP.BasicProperties prop = new AMQP.BasicProperties().builder()
                    //设置消息头
                    .headers(map)
                    //设置消息持久化（默认是1）
                    .deliveryMode(2)
                    //设置过期消息
                    // 处理方式 1.进入死信队列 2.删除
                    .expiration("1000")
                    .build();
            channel.basicPublish("exchange-topic", "topic2.topic1", prop, "topic1".getBytes());
        }
        connection.close();
    }
}
