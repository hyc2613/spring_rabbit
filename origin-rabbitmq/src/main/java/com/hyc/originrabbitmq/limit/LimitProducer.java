package com.hyc.originrabbitmq.limit;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

public class LimitProducer {

    public static void main(String[] args) throws IOException {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();
        Channel channel = RabbitUtil.getChannel(config);

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(String.format("ack! deliveryTag:%d, multiple:%s", deliveryTag, multiple));
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(String.format("Nack! deliveryTag:%d, multiple:%s", deliveryTag, multiple));
            }
        });
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, ("hello_" + i).getBytes());
        }
    }

}
