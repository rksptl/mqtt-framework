package com.rkp.framework.mqtt.exception;

/**
 * Thrown when an MQTT topic is invalid.
 */
public class TopicValidationException extends MqttFrameworkException {

	public TopicValidationException(String message) {
		super(message);
	}

	public TopicValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}