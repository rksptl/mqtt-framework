package com.rkp.framework.mqtt.exception;

/**
 * Thrown when a subscribe or unsubscribe operation fails.
 */
public class SubscribeException extends MqttFrameworkException {

	public SubscribeException(String message) {
		super(message);
	}

	public SubscribeException(String message, Throwable cause) {
		super(message, cause);
	}

}