package com.interview.user.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Setter
@Getter
@Configuration
public class RabbitMQ {

    @Value("${rabbit.mq.hosts}")
    private String amqpHosts;

    @Value("${rabbit.mq.user}")
    private String rabbitMQUserName;

    @Value("AES:${rabbit.mq.password}")
    private String rabbitMQPassword;

    @Value("${rabbit.mq.channel.cache.size:25}")
    private int channelCacheSize;

    @Value("${rabbit.mq.virtual.host:/platform}")
    private String rabbitMQirtualHost;

    @Value("${rabbit.mq.exchange:authentication}")
    private String rabbitMQExchange;
}
