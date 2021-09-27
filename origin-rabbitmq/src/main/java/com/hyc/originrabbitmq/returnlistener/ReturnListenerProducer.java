package com.hyc.originrabbitmq.returnlistener;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Collections;

public class ReturnListenerProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);

//        channel.addReturnListener(new HycReturnListener());
        String errorRoutingKey = "xxx";
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .expiration("10000") // 消息10s后过期，如果没被消费就转入死信队列
                .deliveryMode(2) // 2表示 消息持久化
                .contentEncoding("UTF-8")
                .headers(Collections.singletonMap("sysId", "bmp"))
                .build();
        // mandatory 如果为false，表示如果消息不可达，则broker会自动删除这条消息
        channel.basicPublish(config.getExchangeName(), errorRoutingKey, false, properties, "mandatory=false".getBytes());
        // mandatory 如果为true，表示如果消息不可达，则会调用returnListener来通知生产者
        channel.basicPublish(config.getExchangeName(), errorRoutingKey, true, properties, "mandatory=true".getBytes());
//        channel.close();
//        RabbitUtil.closeResource();
    }

}
