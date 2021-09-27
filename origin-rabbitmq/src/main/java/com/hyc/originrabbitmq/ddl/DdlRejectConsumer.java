package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.common.HycAckConsumer;
import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DdlRejectConsumer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);
        channel.basicConsume(config.getQueueName(), false, new HycAckConsumer(channel));
    }

}
