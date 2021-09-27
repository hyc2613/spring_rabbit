package com.hyc.originrabbitmq.returnlistener;

import com.hyc.originrabbitmq.common.HycDefaultRabbitConsumer;
import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class ReturnListenerConsumer {
    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);
        channel.basicConsume(config.getQueueName(), new HycDefaultRabbitConsumer(channel));
    }
}
