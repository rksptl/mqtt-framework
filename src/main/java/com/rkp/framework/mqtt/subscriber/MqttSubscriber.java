package com.rkp.framework.mqtt.subscriber;

import com.rkp.framework.mqtt.model.SubscribeRequest;

/**
 * MQTT Subscriber API.
 */
public interface MqttSubscriber {

    /**
     * Subscribe to an MQTT topic.
     *
     * @param request subscribe request
     * @param <T> payload type
     */
    <T> void subscribe(SubscribeRequest<T> request);

    /**
     * Unsubscribe from a topic.
     *
     * @param topic topic
     */
    void unsubscribe(String topic);

}