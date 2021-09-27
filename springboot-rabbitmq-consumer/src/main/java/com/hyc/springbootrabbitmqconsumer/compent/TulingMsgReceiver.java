package com.hyc.springbootrabbitmqconsumer.compent;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TulingMsgReceiver {

    @RabbitListener(queues = {"springboot.direct.exchange.queue"})
    @RabbitHandler
    public void consumerMsg(Message message, Channel channel) throws IOException {

        log.info("消费消息:{}",message.getPayload());
        //手工签收
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        log.info("接受deliveryTag:{}",deliveryTag);
        channel.basicAck(deliveryTag,false);
    }

}
