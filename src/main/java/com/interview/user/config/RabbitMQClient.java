package com.interview.user.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

public class RabbitMQClient {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpRequestContextClientPostProcessor postProcessor = new AmqpRequestContextClientPostProcessor();

    public RabbitMQClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public RabbitMQClient(AmqpTemplate amqpTemplate) {
        if (!(amqpTemplate instanceof RabbitTemplate)) {
            throw new IllegalArgumentException("AmqpTemplate should be of RabbitTemplate concrete type or a subclass");
        }
        this.rabbitTemplate = (RabbitTemplate) amqpTemplate;
    }

    public void send(Version version, Message message) throws AmqpException {
        message.getMessageProperties().setContentType(version.getMediaType());
        message.getMessageProperties().setType(formatType(rabbitTemplate.getExchange(), version));
        rabbitTemplate.send(version.getRoutingKey(), postProcessor.postProcessMessage(message));
    }

    public void send(String exchange, Version version, Message message) throws AmqpException {
        message.getMessageProperties().setContentType(version.getMediaType());
        message.getMessageProperties().setType(formatType(exchange, version));
        rabbitTemplate.send(exchange, version.getRoutingKey(), postProcessor.postProcessMessage(message));
    }

    public void convertAndSend(Version version, Object obj) throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(rabbitTemplate.getExchange(), version)));
        rabbitTemplate.convertAndSend(version.getRoutingKey(), obj, postProcessor);
    }

    public void convertAndSend(String exchange, Version version, Object obj) throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(exchange, version)));
        rabbitTemplate.convertAndSend(exchange, version.getRoutingKey(), obj, postProcessor);
    }

    public void convertAndSend(Version version, Object obj, MessagePostProcessor messagePostProcessor)
            throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(rabbitTemplate.getExchange(), version),
                        messagePostProcessor));
        rabbitTemplate.convertAndSend(version.getRoutingKey(), obj, postProcessor);
    }

    public void convertAndSend(String exchange, Version version, Object obj, MessagePostProcessor messagePostProcessor)
            throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(exchange, version), messagePostProcessor));
        rabbitTemplate.convertAndSend(exchange, version.getRoutingKey(), obj, postProcessor);
    }

    public Message sendAndReceive(Version version, Message message) throws AmqpException {
        message.getMessageProperties().setContentType(version.getMediaType());
        message.getMessageProperties().setType(formatType(rabbitTemplate.getExchange(), version));
        return rabbitTemplate.sendAndReceive(version.getRoutingKey(), postProcessor.postProcessMessage(message));
    }

    public Message sendAndReceive(String exchange, Version version, Message message) throws AmqpException {
        message.getMessageProperties().setContentType(version.getMediaType());
        message.getMessageProperties().setType(formatType(exchange, version));
        return rabbitTemplate
                .sendAndReceive(exchange, version.getRoutingKey(), postProcessor.postProcessMessage(message));
    }

    public <T> T convertSendAndReceive(Version version, Object obj, Class<T> responseType) throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(rabbitTemplate.getExchange(), version)));
        Object result = rabbitTemplate.convertSendAndReceive(version.getRoutingKey(), obj, postProcessor);
        if (result != null && !responseType.isInstance(result)) {
            throw new IllegalTypeException(
                    "returned object " + result.toString() + " is not of required type " + responseType.getName());
        }
        return (T) result;
    }

    public <T> T convertSendAndReceive(String exchange, Version version, Object obj, Class<T> responseType)
            throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(exchange, version)));
        Object result = rabbitTemplate.convertSendAndReceive(exchange, version.getRoutingKey(), obj, postProcessor);
        if (result != null && !responseType.isInstance(result)) {
            throw new IllegalTypeException(
                    "returned object " + result.toString() + " is not of required type " + responseType.getName());
        }
        return (T) result;
    }

    public <T> T convertSendAndReceive(Version version, Object obj, MessagePostProcessor messagePostProcessor,
                                       Class<T> responseType) throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(rabbitTemplate.getExchange(), version), messagePostProcessor));
        Object result = rabbitTemplate.convertSendAndReceive(version.getRoutingKey(), obj, postProcessor);
        if (result != null && !responseType.isInstance(result)) {
            throw new IllegalTypeException(
                    "returned object " + result.toString() + " is not of required type " + responseType.getName());
        }
        return (T) result;
    }

    public <T> T convertSendAndReceive(String exchange, Version version, Object obj,
                                       MessagePostProcessor messagePostProcessor, Class<T> responseType) throws AmqpException {
        MessagePostProcessor postProcessor = new CompositeClientPostProcessor(
                createProcessors(version.getMediaType(), formatType(exchange, version), messagePostProcessor));
        Object result = rabbitTemplate.convertSendAndReceive(exchange, version.getRoutingKey(), obj, postProcessor);
        if (result != null && !responseType.isInstance(result)) {
            throw new IllegalTypeException(
                    "returned object " + result.toString() + " is not of required type " + responseType.getName());
        }
        return (T) result;
    }

    private String formatType(String exchange, Version version) {
        return exchange + "." + version.getRoutingKey();
    }

    private List<MessagePostProcessor> createProcessors(String mediaType, String type) {
        return createProcessors(mediaType, type, null);
    }

    private List<MessagePostProcessor> createProcessors(String mediaType, String type, MessagePostProcessor processor) {
        List<MessagePostProcessor> processors = new ArrayList<>();
        if (processor != null) {
            processors.add(processor);
        }
        processors.add(postProcessor);
        processors.add(new MediaTypeClientPostProcessor(mediaType));
        processors.add(new TypeClientPostProcessor(type));
        return processors;
    }
}
