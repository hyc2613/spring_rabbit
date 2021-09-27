package com.hyc.originrabbitmq.confirm;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * confirmListener 用于给生产者一个提示，确认发出的消息是否成功
 */
public class HycConfirmListener implements ConfirmListener {

    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        // multiple可能为true，也可能为false，不一定，是由rabbitmq服务器来决定的。如果为true，则表示该deliveryTag之前的消息都已经被投递成功
        System.out.println("acked! deliveryTag:" + deliveryTag + ",multiple:" + multiple);
    }

    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        // 极少出现 nack，
        System.out.println("Nack! deliveryTag:" + deliveryTag + ",multiple:" + multiple);
    }
}
