package com.hyc.originrabbitmq.limit;

import com.hyc.originrabbitmq.common.HycAckConsumer;
import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LimitConsumer {

    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);

        // 限流，一次只拉取一条消息，当我上一条消息还未ack时，就不会拉取新的消息
        channel.basicQos(0, 1, false);
        // autoAck必须为false，手动来ack，否则限流就没意义了。
        channel.basicConsume(config.getQueueName(), false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                System.out.println("deliveryTag:" + envelope.getDeliveryTag() + ", message:" + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
