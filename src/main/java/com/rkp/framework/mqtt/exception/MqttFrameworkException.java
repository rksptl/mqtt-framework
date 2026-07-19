package com.rkp.framework.mqtt.exception;

/**
 * Base exception for the MQTT Framework.
 */
public class MqttFrameworkException extends RuntimeException {

	public MqttFrameworkException(String message) {
		super(message);
	}

	public MqttFrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

}