package com.hyc.originrabbitmq.config;

public enum RabbitExchangeTypeEnum {
    DIRECT("direct"), FANOUT("fanout"), TOPIC("topic");

    private final String value;

    RabbitExchangeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
