package com.hyc.originrabbitmq.config;

import lombok.Data;

@Data
public class RabbitmqInfoConfig {

    public static final String RABBIT_SERVER_URI = "amqp://guest:guest@127.0.0.1:5672";
    private String exchangeName;
    private String queueName;
    private String routingKey;
    private RabbitExchangeTypeEnum exchangeType;
    private boolean isDurable = true;
    private boolean isAutoDelete = true;

    private static final String DEFAULT_EXCHANGE = "tuling";
    private static final String DEFAULT_QUEUE = "queue.default";
    private static final String DEFAULT_ROUTING_KEY = "test";

    private static final String DEFAULT_TOPIC_ROUTING_KEY = "test.#";
    private static RabbitmqInfoConfig instance;

    public RabbitmqInfoConfig(String exchangeName, String queueName, String routingKey, RabbitExchangeTypeEnum exchangeType) {
        this.exchangeName = exchangeName + "." + exchangeType.getValue();
        this.queueName = this.exchangeName + "." + queueName;
        this.routingKey = routingKey;
        this.exchangeType = exchangeType;
    }

    public static RabbitmqInfoConfig getDlxInstance() {
        if (instance == null) {
            synchronized (RabbitmqInfoConfig.class) {
                if (instance == null) {
                    instance = new RabbitmqInfoConfig("tuling.dlx", "queue", "", RabbitExchangeTypeEnum.FANOUT);
                }
                instance.setAutoDelete(false);
            }
        }
        return instance;
    }

    public static RabbitmqInfoConfig getNoDurableDefaultInstance() {
        if (instance == null) {
            synchronized (RabbitmqInfoConfig.class) {
                if (instance == null) {
                    instance = new RabbitmqInfoConfig(DEFAULT_EXCHANGE, DEFAULT_QUEUE, "test", RabbitExchangeTypeEnum.DIRECT);
                    // 设置队列不持久化，则表示rabbitmq重启后，该队列不会再保留
                    instance.setDurable(false);
                }
            }
        }
        return instance;
    }

    public static RabbitmqInfoConfig getDurableInstance() {
        if (instance == null) {
            synchronized (RabbitmqInfoConfig.class) {
                if (instance == null) {
                    instance = new RabbitmqInfoConfig(DEFAULT_EXCHANGE, "queue.durable", "test.#", RabbitExchangeTypeEnum.DIRECT);
                }
            }
        }
        return instance;
    }

    public String getExchangeType() {
        return exchangeType.getValue();
    }
}
