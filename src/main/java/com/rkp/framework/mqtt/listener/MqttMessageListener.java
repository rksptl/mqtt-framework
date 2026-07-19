package com.rkp.framework.mqtt.listener;

/**
 * Functional interface for receiving MQTT messages.
 *
 * @param <T> payload type
 */
@FunctionalInterface
public interface MqttMessageListener<T> {

    /**
     * Invoked when a message is received.
     *
     * @param topic MQTT topic
     * @param payload converted payload
     */
    void onMessage(String topic, T payload);

}