package com.rkp.framework.mqtt.exception;

/**
 * Thrown when no MQTT client provider is available.
 */
public class ProviderNotFoundException extends MqttFrameworkException {

    public ProviderNotFoundException(String message) {
        super(message);
    }

}
