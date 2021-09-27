package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Collections;

public class DdlExpireProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);
        channel.basicQos(1);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .expiration("10000") // 消息10s后过期，如果没被消费就转入死信队列.所谓的没被消费就是没有消费者去消费，并不包含消费者消费慢导致超时的情况。
                .deliveryMode(2) // 消息持久化
                .contentEncoding("UTF-8")
                .headers(Collections.singletonMap("sysId", "bmp"))
                .build();
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), properties, ("hello_" + i).getBytes());
        }
        channel.close();
        RabbitUtil.closeResource();
    }

}
