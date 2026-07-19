package com.rkp.framework.mqtt.validation;

import com.rkp.framework.mqtt.exception.TopicValidationException;

/**
 * Validates MQTT topics.
 */
public class TopicValidator {

    /**
     * Validate an MQTT topic.
     *
     * @param topic MQTT topic
     */
    public void validate(String topic) {

        if (topic == null || topic.isBlank()) {
            throw new TopicValidationException("MQTT topic must not be null or blank.");
        }

        if (topic.length() > 65535) {
            throw new TopicValidationException("MQTT topic exceeds maximum length.");
        }

        if (topic.contains("\u0000")) {
            throw new TopicValidationException("MQTT topic contains null character.");
        }
    }

}