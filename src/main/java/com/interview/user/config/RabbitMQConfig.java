package com.interview.user.config;

import com.interview.user.common.Constants;
import com.interview.user.common.JsonObjectMapperFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

@Configuration
@Import(RabbitMQ.class)
public class RabbitMQConfig {

    private final RabbitMQ rabbitMQ;

    @Autowired
    public RabbitMQConfig(RabbitMQ rabbitMQ) {
        this.rabbitMQ = rabbitMQ;
    }

    private static CustomizableThreadFactory threadFactory() {
        return new CustomizableThreadFactory("rabbitmq-");
    }

    @Bean
    public RabbitMQClient authnAmqpClient() {
        return new RabbitMQClient(amqpTemplate());
    }

    @Bean
    public RabbitAdmin authnRabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.declareExchange(authnExchange());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(Jackson2JsonMessageConverter amqpMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setMessageConverter(amqpMessageConverter);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter amqpMessageConverter() {
        return new Jackson2JsonMessageConverter(JsonObjectMapperFactory.createMapper());
    }

    private ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitMQ.getAmqpHosts());
        connectionFactory.setUsername(rabbitMQ.getRabbitMQUserName());
        connectionFactory.setPassword(rabbitMQ.getRabbitMQPassword());
        connectionFactory.setVirtualHost(rabbitMQ.getRabbitMQirtualHost());
        connectionFactory.setConnectionThreadFactory(threadFactory());
        connectionFactory.setChannelCacheSize(rabbitMQ.getChannelCacheSize());
        return connectionFactory;
    }

    private RabbitTemplate amqpTemplate() {
        RabbitTemplate amqpTemplate = new RabbitTemplate(connectionFactory());
        amqpTemplate.setExchange(rabbitMQ.getRabbitMQExchange());
        amqpTemplate.setMessageConverter(amqpMessageConverter());
        return amqpTemplate;
    }

    private Exchange authnExchange() {
        return new TopicExchange(Constants.EXCHANGE);
    }
}
