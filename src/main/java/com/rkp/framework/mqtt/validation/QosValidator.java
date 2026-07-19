package com.rkp.framework.mqtt.validation;

import com.rkp.framework.mqtt.exception.MqttConfigurationException;
import com.rkp.framework.mqtt.model.Qos;

/**
 * Validates MQTT QoS values.
 */
public class QosValidator {

    /**
     * Validate QoS.
     *
     * @param qos MQTT QoS
     */
    public void validate(Qos qos) {

        if (qos == null) {
            throw new MqttConfigurationException("QoS must not be null.");
        }

    }

    /**
     * Validate integer QoS.
     */
    public void validate(int qos) {

        if (qos < 0 || qos > 2) {
            throw new MqttConfigurationException(
                    "Invalid QoS value: " + qos + ". Valid values are 0, 1 and 2.");
        }

    }

}