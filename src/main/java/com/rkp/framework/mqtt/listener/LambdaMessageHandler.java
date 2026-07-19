package com.rkp.framework.mqtt.listener;

import java.util.Objects;

/**
 * Message handler backed by a lambda listener.
 *
 * @param <T> payload type
 */
public class LambdaMessageHandler<T> implements MessageHandler<T> {

    private final MqttMessageListener<T> listener;

    public LambdaMessageHandler(MqttMessageListener<T> listener) {

        this.listener = Objects.requireNonNull(listener);

    }

    @Override
    public void handle(String topic, T payload) {

        listener.onMessage(topic, payload);

    }

}