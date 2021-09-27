package com.hyc.springrabbitmq;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = SpringRabbitmqApplication.class)
@RunWith(SpringRunner.class)
class SpringRabbitmqApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testTopicExchange() {
        //声明一个交换机
        TopicExchange topicExchange = new TopicExchange("rabbitadmin.topic.exchange",true,false);
        rabbitAdmin.declareExchange(topicExchange);

        //申明一个队列
        Queue queue = new Queue("rabbitadmin.topic.queue",true);
        rabbitAdmin.declareQueue(queue);

        //申明一个绑定
        Binding binding = new Binding("rabbitadmin.topic.queue",Binding.DestinationType.QUEUE,
                "rabbitadmin.topic.exchange","rabbitadmin.#",null);
        rabbitAdmin.declareBinding(binding);
    }

    @Test
    public void testDirectExchange() {
        DirectExchange directExchange = new DirectExchange("rabbitadmin.direct.exchange",true,false);
        rabbitAdmin.declareExchange(directExchange);
        Queue queue = new Queue("rabbitadmin.direct.queue",true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with("rabbitadmin.key.#"));

    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitmqTemplate() {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("company","tuling");
        messageProperties.getHeaders().put("name","smlz");

        String msgBody = "hello tuling";
        Message message = new Message(msgBody.getBytes(),messageProperties);

        //rabbitTemplate.send("tuling.topic.exchange","topic.haha",message);

        //不需要message对象发送
        rabbitTemplate.convertAndSend("tuling.direct.exchange","direct.key","smlz");
    }

}
