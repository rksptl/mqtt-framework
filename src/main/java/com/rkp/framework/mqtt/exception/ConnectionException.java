package com.rkp.framework.mqtt.exception;

/**
 * Thrown when the framework cannot connect or disconnect from the MQTT broker.
 */
public class ConnectionException extends MqttFrameworkException {

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}