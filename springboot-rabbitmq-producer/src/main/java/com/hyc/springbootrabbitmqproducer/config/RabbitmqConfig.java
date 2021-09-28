package com.hyc.springbootrabbitmqproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    @Bean
    public DirectExchange tulingBootDirectExchange() {
        DirectExchange directExchange = new DirectExchange("springboot.direct.exchange",true,false);
        return directExchange;
    }

    @Bean
    public DirectExchange tulingDirectExchange() {
        DirectExchange directExchange = new DirectExchange("tuling.direct.exchange",true,false);
        return directExchange;
    }

    @Bean
    public Queue tulingBootQueue() {
        Queue queue = new Queue("springboot.direct.exchange.queue",true,false,false);
        return queue;
    }

    @Bean
    public Queue tulingDirectQueue() {
        Queue queue = new Queue("tuling.direct.exchange.queue",true,false,false);
        return queue;
    }

    @Bean
    public Binding tulingBootBinder() {
        return BindingBuilder.bind(tulingBootQueue()).to(tulingBootDirectExchange()).with("springboot.key");
    }

    @Bean
    public Binding tulingDirectBinder() {
        return BindingBuilder.bind(tulingDirectQueue()).to(tulingDirectExchange()).with("tuling.key");
    }

}
