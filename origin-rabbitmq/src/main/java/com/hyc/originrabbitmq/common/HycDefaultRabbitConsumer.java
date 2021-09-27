package com.hyc.originrabbitmq.common;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 默认的消息消费者，只是用来打印信息
 */
public class HycDefaultRabbitConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public HycDefaultRabbitConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        printInfo(consumerTag, envelope, properties, new String(body));
    }

    private void printInfo(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, String messageBody) {
        StringBuilder sb = new StringBuilder();
        properties.appendPropertyDebugStringTo(sb);
        System.out.println(String.format("consumerTag=%s, evelope=%s, properties=%s, message=%s", consumerTag, envelope.toString(), sb.toString(), messageBody));
    }

}
