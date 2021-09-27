package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class DlxQueueConsumer {

    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getDlxInstance();
        Channel channel = RabbitUtil.getChannel(config);

        channel.basicConsume(config.getQueueName(), true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                System.out.println("dlx=" + new String(body));
            }
        });
    }
}
