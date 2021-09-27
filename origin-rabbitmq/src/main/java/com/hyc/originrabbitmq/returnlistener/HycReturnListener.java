package com.hyc.originrabbitmq.returnlistener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

public class HycReturnListener implements ReturnListener {

//    @Override
//    public void handle(Return returnMessage) {
//        System.out.println(String.format("已送达。 msg=%s, replyCode=%s, replyText=%s, routingKey=%s", new String(returnMessage.getBody()), returnMessage.getReplyCode(), returnMessage.getReplyText(), returnMessage.getRoutingKey()));
//    }

    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(String.format("未送达。 msg=%s, replyCode=%s, replyText=%s, routingKey=%s", new String(body), replyCode, replyText, routingKey));
    }
}
