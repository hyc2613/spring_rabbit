package com.hyc.originrabbitmq.ddl;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class DdlExpireConsumer {

    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getDurableInstance();
        Channel channel = RabbitUtil.getChannel(config);

        channel.basicConsume(config.getQueueName(), true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                System.out.println(new String(body));
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                channel.basicReject(envelope.getDeliveryTag(), false);
            }
        });
    }
}
