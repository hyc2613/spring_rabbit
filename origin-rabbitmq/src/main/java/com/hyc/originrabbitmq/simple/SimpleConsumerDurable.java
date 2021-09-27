package com.hyc.originrabbitmq.simple;

import com.hyc.originrabbitmq.common.HycDefaultRabbitConsumer;
import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

public class SimpleConsumerDurable {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getDurableInstance();
        Channel channel = RabbitUtil.getChannel(config);

        channel.basicConsume(config.getQueueName(), true, new HycDefaultRabbitConsumer(channel));
//        channel.close();
//        connection.close();
    }


}
