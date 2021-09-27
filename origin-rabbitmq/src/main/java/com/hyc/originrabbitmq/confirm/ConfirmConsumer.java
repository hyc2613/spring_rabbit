package com.hyc.originrabbitmq.confirm;

import com.hyc.originrabbitmq.common.HycAckConsumer;
import com.hyc.originrabbitmq.common.HycDefaultRabbitConsumer;
import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class ConfirmConsumer {

    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);

//        channel.basicQos(1, false);
        channel.basicConsume(config.getQueueName(), true, new HycDefaultRabbitConsumer(channel));
//        channel.basicConsume(config.getQueueName(), true, new HycAckConsumer(channel));
    }

}
