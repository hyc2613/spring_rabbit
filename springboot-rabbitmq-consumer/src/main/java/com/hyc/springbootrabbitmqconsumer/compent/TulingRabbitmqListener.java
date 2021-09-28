package com.hyc.springbootrabbitmqconsumer.compent;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class TulingRabbitmqListener {

    @RabbitListener(queues = {"tuling.direct.exchange.queue"})
    public void directMsgListener(@Payload String payLoad, @Headers Map<String, Object> heads, Channel channel) throws IOException {
        long deliveryTag = (long) heads.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("payLoad:"+payLoad+", deliveryTag="+deliveryTag);
        // 这里手动ack，是因为appliction.properties中设置了 spring.rabbitmq.listener.simple.acknowledge-mode=manual
//        channel.basicAck(deliveryTag, false);
    }


}
