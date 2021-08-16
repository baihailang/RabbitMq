package com.baihailang.provider.mq01simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//RabbitMq单消息生产者
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        //设置虚拟主机类似mysql中的数据库·
        connectionFactory.setVirtualHost("/");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //连接创建信道:一个连接有多个信道
        Channel channel = connection.createChannel();
        //声明队列名字
        String quename="SimpleQue";
        //定义一个队列（没有交换机模式）
        // 1.String 队列名字，
        // 2.boolean durable 队列是否持久化，重启服务器之后队列是否还在
        // 3.boolean exclusive  是否是独占队列，是否只能当前连接能消费
        // 4.autodelete  队列用完之后是否删除
        // 5.Map队列的附加属性
        channel.queueDeclare(quename,true,false,false,null);
        //1.String 交换机名字（简单模式不能写null）
        //2.String 路由key，交换机根据路由匹配规则将消息发送到指定队列，简单模式的路由key就是队列名字
        //3. 消息的附加属性
        //4.消息体
        channel.basicPublish("",quename,null,"你好啊白海浪".getBytes());
        System.out.println("发送成功");
        channel.close();
    }
}
