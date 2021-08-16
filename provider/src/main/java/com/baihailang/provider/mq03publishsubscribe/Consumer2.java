package com.baihailang.provider.mq03publishsubscribe;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者2
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
        ////定义交换机（发布者订阅模式就是fanout）
        channel.exchangeDeclare("exchange-fanout", BuiltinExchangeType.FANOUT);
        //定义队列
        channel.queueDeclare("que-publish-subscribe2", true, false, false, null);
        //1.定义队列的名字
        //2.交换机的名字
        //3.路由key fanout模式即发布者订阅模式默认写成:""
        channel.queueBind("que-publish-subscribe2", "exchange-fanout", "");
        channel.basicConsume("que-publish-subscribe2",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2");
                System.out.println("body==========》" + new String(body));
            }
        });
    }
}
