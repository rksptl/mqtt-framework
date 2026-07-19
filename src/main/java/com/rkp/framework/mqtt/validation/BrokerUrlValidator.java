package com.rkp.framework.mqtt.validation;

import java.net.URI;
import java.net.URISyntaxException;

import com.rkp.framework.mqtt.exception.MqttConfigurationException;

/**
 * Validates MQTT broker URLs.
 */
public class BrokerUrlValidator {

	/**
	 * Validate broker URL.
	 *
	 * @param brokerUrl MQTT broker URL
	 */
	public void validate(String brokerUrl) {

		if (brokerUrl == null || brokerUrl.isBlank()) {
			throw new MqttConfigurationException("Broker URL must not be null or blank.");
		}

		try {

			URI uri = new URI(brokerUrl);

			String scheme = uri.getScheme();

			if (scheme == null) {
				throw new MqttConfigurationException("Broker URL must include a scheme.");
			}

			switch (scheme.toLowerCase()) {

			case "tcp":
			case "ssl":
			case "ws":
			case "wss":
				break;

			default:
				throw new MqttConfigurationException("Unsupported broker protocol: " + scheme);

			}

			if (uri.getHost() == null || uri.getHost().isBlank()) {
				throw new MqttConfigurationException("Broker host is missing.");
			}

		} catch (URISyntaxException ex) {

			throw new MqttConfigurationException("Invalid broker URL: " + brokerUrl, ex);

		}

	}

}