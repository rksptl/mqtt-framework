package com.rkp.framework.mqtt.exception;

/**
 * Thrown when an object cannot be serialized into JSON.
 */
public class SerializationException extends MqttFrameworkException {

	public SerializationException(String message) {
		super(message);
	}

	public SerializationException(String message, Throwable cause) {
		super(message, cause);
	}

}