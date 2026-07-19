package com.rkp.framework.mqtt.publisher;

import com.rkp.framework.mqtt.model.PublishRequest;

/**
 * MQTT Publisher API.
 */
public interface MqttPublisher {

	/**
	 * Publish a message.
	 *
	 * @param request publish request
	 */
	void publish(PublishRequest request);

}