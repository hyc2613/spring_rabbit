package com.hyc.springbootrabbitmqproducer.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class TulingRetrunCallBack implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("correlationId:{}",message.getMessageProperties().getCorrelationId());
        log.warn("replyText:{}",replyText);
        log.warn("消息内容:{}",new String(message.getBody()));
        log.warn("replyCode:{}",replyCode);
        log.warn("交换机:{}",exchange);
        log.warn("routingKey:{}",routingKey);
        log.info("需要更新数据库日志表得消息记录为不可达");
    }

}
