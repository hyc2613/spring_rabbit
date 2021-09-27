package com.hyc.springrabbitmq.config;

import com.hyc.springrabbitmq.messagedelegate.TulingMessageDelegate;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RabbitmqConfig {

    public static final String RABBIT_SERVER_URI = "amqp://guest:guest@127.0.0.1:5672";

    @Bean
    public ConnectionFactory connectionFactory () {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(RABBIT_SERVER_URI);
        cachingConnectionFactory.setConnectionTimeout(100000);
        cachingConnectionFactory.setCloseTimeout(100000);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //spring容器启动加载该类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    //=====================================申明三个交换机====================================================================
    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange("tuling.topic.exchange",true,false);
        return topicExchange;
    }

    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("tuling.direct.exchange",true,false);
        return directExchange;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("tuling.fanout.exchange",true,false);
        return fanoutExchange;
    }

    //===========================================申明队列===========================================================
    @Bean
    public Queue testTopicQueue() {
        Queue queue = new Queue("tuling.topic.exchange.queue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue testDirectQueue() {
        Queue queue = new Queue("tuling.direct.exchange.queue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue testFanoutQueue() {
        Queue queue = new Queue("tuling.fanout.exchange.queue",true,false,false,null);
        return queue;
    }

    public Queue orderQueue() {
        Queue queue = new Queue("orderQueue",true,false,false,null);
        return queue;
    }

    @Bean
    public Queue fileQueue() {
        Queue queue = new Queue("fileQueue",true,false,false,null);
        return queue;
    }

    //================================================申明绑定===========================================================
    public Binding topicBinding() {
        return BindingBuilder.bind(testDirectQueue()).to(topicExchange()).with("topic.key.#");
    }

    public Binding directBinding() {
        return BindingBuilder.bind(testDirectQueue()).to(directExchange()).with("direct.key");
    }

    public Binding fanoutBinding() {
        return BindingBuilder.bind(testFanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(orderQueue()).to(directExchange()).with("rabbitmq.order");
    }

    @Bean
    public Binding fileQueueBinding() {
        return BindingBuilder.bind(fileQueue()).to(directExchange()).with("rabbitmq.file");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReceiveTimeout(50000);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        simpleMessageListenerContainer.setQueues(testDirectQueue(), testTopicQueue(), testFanoutQueue(), orderQueue(), fileQueue());
        //设置当前消费者1
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        //最大消费者个数5
        simpleMessageListenerContainer.setMaxConcurrentConsumers(10);
        //设置签收模式
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置拒绝重回队列
        simpleMessageListenerContainer.setDefaultRequeueRejected(false);
        //消费端的标签策略
        simpleMessageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return UUID.randomUUID()+"-"+s;
            }
        });

        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new TulingMessageDelegate());
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);

        return simpleMessageListenerContainer;
    }
}
