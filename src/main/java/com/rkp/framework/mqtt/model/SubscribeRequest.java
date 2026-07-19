package com.rkp.framework.mqtt.model;

import com.rkp.framework.mqtt.listener.MqttMessageListener;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request object used to subscribe to an MQTT topic.
 *
 * @param <T> payload type
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SubscribeRequest<T> {

	/**
	 * MQTT topic.
	 */
	private final String topic;

	/**
	 * Java class representing the incoming message type.
	 */
	private final Class<T> messageType;

	/**
	 * Listener that receives messages.
	 */
	private final MqttMessageListener<T> listener;

	/**
	 * MQTT Quality of Service.
	 */
	@Builder.Default
	private final Qos qos = Qos.AT_LEAST_ONCE;

}