package com.hyc.originrabbitmq.common;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class HycAckConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public HycAckConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        String message = new String(body);
        Channel channel = getChannel();
        if (message.startsWith("reject")) {
            // 拒绝一条消息
            System.out.println("reject, deliveryTag:" + envelope.getDeliveryTag());
            channel.basicReject(envelope.getDeliveryTag(), false);
        } else if (message.startsWith("nack")) {
            // 批量拒绝
            System.out.println("nack, deliveryTag:" + envelope.getDeliveryTag());

            channel.basicNack(envelope.getDeliveryTag(), false, false);
        } else {
            System.out.println("ack, deliveryTag:" + envelope.getDeliveryTag());
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }
}
