package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class DdlMaxLengthProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Map<String, Object> argMap = new HashMap<>();
        // 设置队列的最大长度
        argMap.put("x-max-length", 5);
        Channel channel = RabbitUtil.getChannel(config, argMap);
        for (int i = 0; i < 16; i++) {
            // 设置队列最长为5，这里直接塞10个，那么最早被塞进去的消息就会被传入死信队列
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, ("hello_"+i).getBytes());
        }
        channel.close();
        RabbitUtil.closeResource();
    }

}
