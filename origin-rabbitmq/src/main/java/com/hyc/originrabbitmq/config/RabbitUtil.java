package com.hyc.originrabbitmq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class RabbitUtil {

    private static ConnectionFactory factory;
    private static Connection connection;

    private RabbitUtil() {
    }

    private static void init() throws Exception {
        factory = new ConnectionFactory();
        factory.setUri(RabbitmqInfoConfig.RABBIT_SERVER_URI);
        connection = factory.newConnection();
    }

    public static Channel getChannel(RabbitmqInfoConfig config) {
        try {
            if (connection == null) {
                init();
            }
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(config.getExchangeName(), config.getExchangeType());
            // durable 为true，表示该队列会持久化，即如果rabbitmq宕机，下次启动依然会恢复该队列。如果为false，则表示如果rabbitmq宕机，那么该队列不会自动恢复，会重新创建。
            // autoDelete 为true表示如果没有相应的消费者，就自动删除该队列。 为false，则表示即使没有消费者队列依然会保留
            channel.queueDeclare(config.getQueueName(), config.isDurable(), false, config.isAutoDelete(), null);
            channel.queueBind(config.getQueueName(), config.getExchangeName(), config.getRoutingKey());
            return channel;
        } catch (Exception e) {
            log.info("获取rabbit链接出错！");
            throw new RuntimeException(e);
        }
    }

    public static Channel getChannel(RabbitmqInfoConfig config, Map<String, Object> queueArgs) {
        try {
            if (connection == null) {
                init();
            }
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(config.getExchangeName(), config.getExchangeType());
            // durable 为true，表示该队列会持久化，即如果rabbitmq宕机，下次启动依然会恢复该队列。如果为false，则表示如果rabbitmq宕机，那么该队列不会自动恢复，会重新创建。
            // autoDelete 为true表示如果没有相应的消费者，就自动删除该队列。 为false，则表示即使没有消费者队列依然会保留
            channel.queueDeclare(config.getQueueName(), config.isDurable(), false, config.isAutoDelete(), queueArgs);
            channel.queueBind(config.getQueueName(), config.getExchangeName(), config.getRoutingKey());
            return channel;
        } catch (Exception e) {
            log.info("获取rabbit链接出错！");
            throw new RuntimeException(e);
        }
    }

    public static Channel getChannelWithNoDeclare() {
        try {
            if (connection == null) {
                init();
            }
            Channel channel = connection.createChannel();
            return channel;
        } catch (Exception e) {
            log.info("获取rabbit链接出错！");
            throw new RuntimeException(e);
        }
    }

    public static void closeResource() throws Exception {
        connection.close();
    }
}
