package com.rkp.framework.mqtt.exception;

/**
 * Thrown when JSON cannot be converted into an object.
 */
public class DeserializationException extends MqttFrameworkException {

	public DeserializationException(String message) {
		super(message);
	}

	public DeserializationException(String message, Throwable cause) {
		super(message, cause);
	}

}