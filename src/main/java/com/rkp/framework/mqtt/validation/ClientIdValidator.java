package com.rkp.framework.mqtt.validation;

import com.rkp.framework.mqtt.exception.MqttConfigurationException;

/**
 * Validates MQTT client identifiers.
 */
public class ClientIdValidator {

    /**
     * Validate client identifier.
     *
     * @param clientId MQTT client identifier
     */
    public void validate(String clientId) {

        if (clientId == null || clientId.isBlank()) {
            throw new MqttConfigurationException(
                    "MQTT client ID must not be null or blank.");
        }

        if (clientId.length() > 65535) {
            throw new MqttConfigurationException(
                    "MQTT client ID exceeds maximum length.");
        }

    }

}