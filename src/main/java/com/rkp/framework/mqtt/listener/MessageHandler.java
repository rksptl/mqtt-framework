package com.rkp.framework.mqtt.listener;

/**
 * Internal abstraction for all message handlers.
 *
 * @param <T> payload type
 */
public interface MessageHandler<T> {

    /**
     * Handle incoming message.
     *
     * @param topic topic
     * @param payload payload
     */
    void handle(String topic, T payload);

}