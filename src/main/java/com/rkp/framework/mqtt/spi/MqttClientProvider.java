package com.rkp.framework.mqtt.spi;

import com.rkp.framework.mqtt.model.Qos;

/**
 * Service Provider Interface for MQTT clients.
 *
 * Implementations are responsible for connecting to MQTT brokers and performing
 * publish/subscribe operations.
 */
public interface MqttClientProvider {

	/**
	 * Connect to broker.
	 */
	void connect();

	/**
	 * Disconnect from broker.
	 */
	void disconnect();

	/**
	 * Returns whether the client is connected.
	 *
	 * @return true if connected
	 */
	boolean isConnected();

	/**
	 * Publish a message.
	 *
	 * @param topic    MQTT topic
	 * @param payload  JSON payload
	 * @param qos      MQTT QoS
	 * @param retained retained flag
	 */
	void publish(String topic, String payload, Qos qos, boolean retained);

	/**
	 * Subscribe to topic.
	 *
	 * @param topic MQTT topic
	 * @param qos   MQTT QoS
	 */
	void subscribe(String topic, Qos qos);

	/**
	 * Unsubscribe from topic.
	 *
	 * @param topic MQTT topic
	 */
	void unsubscribe(String topic);

}