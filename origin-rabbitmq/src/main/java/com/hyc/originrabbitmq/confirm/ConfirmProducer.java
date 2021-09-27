package com.hyc.originrabbitmq.confirm;

import com.hyc.originrabbitmq.config.RabbitUtil;
import com.hyc.originrabbitmq.config.RabbitmqInfoConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class ConfirmProducer {

    public static void main(String[] args) throws Exception {
        RabbitmqInfoConfig config = RabbitmqInfoConfig.getNoDurableDefaultInstance();

        Channel channel = RabbitUtil.getChannel(config);

        // 设置confirm模式
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        // 增加confirm监听器。注意：这里的监听器只是监听消息是否被投递到rabbitmq服务器上，他跟consumer是否ack无关。
        channel.addConfirmListener(new HycConfirmListener());
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(config.getExchangeName(), config.getRoutingKey(), null, ("hello_" + i).getBytes());
        }
        // 这里不能马上关闭，要等confirmListener确认。
//        channel.close();
//        RabbitUtil.closeResource();
    }

}
