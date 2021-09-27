package com.hyc.originrabbitmq.simple;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.util.Collections;
import java.util.UUID;

public class SimpleProducerDurable {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getDurableInstance();
        Channel channel = RabbitUtil.getChannel(config);
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties = properties.builder().headers(Collections.singletonMap("flag", "confirmProducer")).messageId(UUID.randomUUID().toString()).deliveryMode(2).contentType("html/text").contentEncoding("UTF-8").build();

        channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), properties, "hello, durableQueue".getBytes());

        channel.close();
        RabbitUtil.closeResource();
    }
}
