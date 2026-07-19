package com.rkp.framework.mqtt.spi;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rkp.framework.mqtt.config.MqttProperties;
import com.rkp.framework.mqtt.exception.ConnectionException;
import com.rkp.framework.mqtt.exception.PublishException;
import com.rkp.framework.mqtt.exception.SubscribeException;
import com.rkp.framework.mqtt.listener.MessageDispatcher;
import com.rkp.framework.mqtt.model.Qos;
import com.rkp.framework.mqtt.validation.BrokerUrlValidator;
import com.rkp.framework.mqtt.validation.ClientIdValidator;

/**
 * Eclipse Paho implementation of {@link MqttClientProvider}.
 */
public class PahoMqttClientProvider implements MqttClientProvider, MqttCallbackExtended {

	private static final Logger log = LoggerFactory.getLogger(PahoMqttClientProvider.class);

	private final MqttProperties properties;

	private final MessageDispatcher dispatcher;

	private final BrokerUrlValidator brokerValidator;

	private final ClientIdValidator clientValidator;

	private final MqttClient client;

	public PahoMqttClientProvider(MqttProperties properties, MessageDispatcher dispatcher,
			BrokerUrlValidator brokerValidator, ClientIdValidator clientValidator) {

		try {

			this.properties = Objects.requireNonNull(properties);
			this.dispatcher = Objects.requireNonNull(dispatcher);
			this.brokerValidator = Objects.requireNonNull(brokerValidator);
			this.clientValidator = Objects.requireNonNull(clientValidator);

			brokerValidator.validate(properties.getBrokerUrl());
			clientValidator.validate(properties.getClientId());

			this.client = new MqttClient(properties.getBrokerUrl(), properties.getClientId());

			this.client.setCallback(this);

		} catch (Exception ex) {

			throw new ConnectionException("Unable to initialize MQTT client.", ex);

		}

	}

	@Override
	public synchronized void connect() {

		if (client.isConnected()) {
			return;
		}

		try {

			MqttConnectOptions options = new MqttConnectOptions();

			options.setAutomaticReconnect(properties.isAutomaticReconnect());

			options.setCleanSession(properties.isCleanSession());

			options.setConnectionTimeout(properties.getConnectionTimeout());

			options.setKeepAliveInterval(properties.getKeepAliveInterval());

			if (properties.getUsername() != null) {

				options.setUserName(properties.getUsername());

			}

			if (properties.getPassword() != null) {

				options.setPassword(properties.getPassword().toCharArray());

			}

			client.connect(options);

			log.info("Connected to MQTT broker.");

		} catch (Exception ex) {

			throw new ConnectionException("Failed to connect to MQTT broker.", ex);

		}

	}

	@Override
	public synchronized void disconnect() {

		try {

			if (client.isConnected()) {

				client.disconnect();

			}

		} catch (Exception ex) {

			throw new ConnectionException("Failed to disconnect MQTT broker.", ex);

		}

	}

	@Override
	public boolean isConnected() {

		return client.isConnected();

	}

	@Override
	public void publish(String topic, String payload, Qos qos, boolean retained) {

		try {

			MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));

			message.setQos(qos.getValue());
			message.setRetained(retained);

			client.publish(topic, message);

		} catch (Exception ex) {

			throw new PublishException("Failed to publish MQTT message.", ex);

		}

	}

	@Override
	public void subscribe(String topic, Qos qos) {

		try {

			client.subscribe(topic, qos.getValue());

		} catch (Exception ex) {

			throw new SubscribeException("Failed to subscribe to topic: " + topic, ex);

		}

	}

	@Override
	public void unsubscribe(String topic) {

		try {

			client.unsubscribe(topic);

		} catch (Exception ex) {

			throw new SubscribeException("Failed to unsubscribe from topic: " + topic, ex);

		}

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {

		dispatcher.dispatch(topic, message.getPayload());

	}

	@Override
	public void connectionLost(Throwable cause) {

		log.warn("MQTT connection lost.", cause);

	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {

		log.info("MQTT connection established. reconnect={}, server={}", reconnect, serverURI);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

		log.debug("MQTT delivery completed.");

	}

}