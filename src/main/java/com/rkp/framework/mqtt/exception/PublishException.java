package com.rkp.framework.mqtt.exception;

/**
 * Thrown when publishing an MQTT message fails.
 */
public class PublishException extends MqttFrameworkException {

	public PublishException(String message) {
		super(message);
	}

	public PublishException(String message, Throwable cause) {
		super(message, cause);
	}

}