package com.hyc.originrabbitmq.simple;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.*;

import java.util.Collections;
import java.util.UUID;

public class SimpleProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);
        AMQP.BasicProperties properties = new AMQP.BasicProperties();
        properties = properties.builder().headers(Collections.singletonMap("flag", "confirmProducer")).messageId(UUID.randomUUID().toString()).deliveryMode(2).contentType("html/text").contentEncoding("UTF-8").build();

        channel.basicPublish(config.getExchangeName(), "test.aaa", properties, "hello, noDurableQueue".getBytes());
        channel.basicPublish(config.getExchangeName(), "test", properties, "hello, 11noDurableQueue".getBytes());

        channel.close();
        RabbitUtil.closeResource();
    }
}
