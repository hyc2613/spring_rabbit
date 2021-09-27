package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

import java.util.Collections;

public class DdlRejectProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);
        channel.basicQos(1);
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
//                .expiration("10000") // 消息10s后过期，如果没被消费就转入死信队列
//                .deliveryMode(2) // 消息持久化
//                .contentEncoding("UTF-8")
//                .headers(Collections.singletonMap("sysId", "bmp"))
//                .build();
        String[] strArray = {"rejectMessage", "normalMessage", "nackMessage"};
        for (String s : strArray) {
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, s.getBytes());

        }
    }

}
