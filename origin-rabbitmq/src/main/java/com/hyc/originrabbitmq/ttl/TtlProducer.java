package com.hyc.originrabbitmq.ttl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TtlProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        // 没有消费者（注意是没有消费者，只要有消费者就不会触发队列超时的特性），则等待5秒后，将其投递到死信队列。
        Map<String, Object> argMap = new HashMap<>();
        // 设置队列超时时间
        argMap.put("x-message-ttl", 5000);
        // 设置队列最大长度
        argMap.put("x-max-length", 5);
        Channel channel = RabbitUtil.getChannel(config, argMap);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, ("hello_" + i).getBytes());
        }
        channel.close();
        RabbitUtil.closeResource();
    }

}
