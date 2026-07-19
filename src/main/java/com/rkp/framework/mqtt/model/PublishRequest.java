package com.rkp.framework.mqtt.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request object used to publish an MQTT message.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PublishRequest {

	/**
	 * MQTT topic.
	 */
	private final String topic;

	/**
	 * Payload object.
	 */
	private final Object payload;

	/**
	 * MQTT Quality of Service.
	 */
	@Builder.Default
	private final Qos qos = Qos.AT_LEAST_ONCE;

	/**
	 * Retained message flag.
	 */
	@Builder.Default
	private final boolean retained = false;

}