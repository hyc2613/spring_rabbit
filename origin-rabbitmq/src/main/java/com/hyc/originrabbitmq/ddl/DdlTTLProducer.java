package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class DdlTTLProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Map<String, Object> argMap = new HashMap<>();
        // 设置队列消息的最长等待时间
        argMap.put("x-message-ttl", 5000);
        Channel channel = RabbitUtil.getChannel(config, argMap);
        for (int i = 0; i < 15; i++) {
            // 塞5条消息，并且没有消费者来消费，5秒后，直接进死信队列
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, ("hello_"+i).getBytes());
        }
        channel.close();
        RabbitUtil.closeResource();
    }

}
