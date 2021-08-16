package com.baihailang.provider.mq01simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式消费者
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //1.队列名字
        //2.是否自动Ack消息确认
        //      true:消费者收到消息后会自动回复给服务器，服务器自动删除消息
        //      false:消费者接收到消息后不会自动回复，服务器不会自动删除消息,需要程序员去手动确认
        //3.回调函数
        channel.basicConsume("SimpleQue",true,new DefaultConsumer(channel){
            // consumerTag 1.确认标记
            // envelope 2.信封
            // properties 3.消息附件信息
            // body 4.消息体
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("consumerTag"+consumerTag);
                System.out.println("envelope"+envelope);
                System.out.println("properties"+properties);
                System.out.println("body"+new String(body));
            }
        });

    }
}
