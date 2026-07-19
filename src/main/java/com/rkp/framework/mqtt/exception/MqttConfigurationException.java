package com.rkp.framework.mqtt.exception;

/**
 * Thrown when MQTT configuration is invalid.
 */
public class MqttConfigurationException extends MqttFrameworkException {

    public MqttConfigurationException(String message) {
        super(message);
    }

    public MqttConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
