package com.baihailang.provider.mq03publishsubscribe;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
        //定义队列
        channel.queueDeclare("que-publish-subscribe1", true, false, false, null);
        //定义交换机 1.交换机名字,2.模式选择
        channel.exchangeDeclare("exchange-publish-subscribe", BuiltinExchangeType.FANOUT);
        //绑定1,队列 2,交换机 3,路由key（fanout模式默认为""）
        channel.queueBind("que-publish-subscribe1", "exchange-fanout", "");
        //消费消息
        channel.basicConsume("que-publish-subscribe1",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者1");
                System.out.println("body==========》" + new String(body));
            }
        });
    }
}
