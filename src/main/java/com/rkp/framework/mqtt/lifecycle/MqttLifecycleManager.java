package com.rkp.framework.mqtt.lifecycle;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rkp.framework.mqtt.exception.ConnectionException;
import com.rkp.framework.mqtt.model.ConnectionState;
import com.rkp.framework.mqtt.model.SubscribeRequest;
import com.rkp.framework.mqtt.spi.MqttClientProvider;
import com.rkp.framework.mqtt.subscriber.SubscriptionManager;

/**
 * Controls the lifecycle of the MQTT framework.
 */
public class MqttLifecycleManager {

	private static final Logger log = LoggerFactory.getLogger(MqttLifecycleManager.class);

	private final MqttClientProvider provider;

	private final SubscriptionManager subscriptionManager;

	private final AtomicReference<ConnectionState> state = new AtomicReference<>(ConnectionState.DISCONNECTED);

	public MqttLifecycleManager(MqttClientProvider provider, SubscriptionManager subscriptionManager) {

		this.provider = Objects.requireNonNull(provider);
		this.subscriptionManager = Objects.requireNonNull(subscriptionManager);

	}

	/**
	 * Starts the MQTT framework.
	 */
	public synchronized void start() {

		if (state.get() == ConnectionState.CONNECTED) {
			return;
		}

		log.info("Starting MQTT framework...");

		state.set(ConnectionState.CONNECTING);

		try {

			provider.connect();

			restoreSubscriptions();

			state.set(ConnectionState.CONNECTED);

			log.info("MQTT framework started successfully.");

		} catch (Exception ex) {

			state.set(ConnectionState.DISCONNECTED);

			throw new ConnectionException("Failed to start MQTT framework.", ex);

		}

	}

	/**
	 * Stops the MQTT framework.
	 */
	public synchronized void stop() {

		if (state.get() == ConnectionState.CLOSED) {
			return;
		}

		log.info("Stopping MQTT framework...");

		try {

			provider.disconnect();

		} finally {

			state.set(ConnectionState.CLOSED);

		}

	}

	/**
	 * Reconnect to broker.
	 */
	public synchronized void reconnect() {

		log.info("Reconnecting MQTT broker...");

		state.set(ConnectionState.RECONNECTING);

		provider.disconnect();

		provider.connect();

		restoreSubscriptions();

		state.set(ConnectionState.CONNECTED);

		log.info("MQTT reconnect completed.");

	}

	/**
	 * Restore all subscriptions after reconnect.
	 */
	protected void restoreSubscriptions() {

		for (SubscribeRequest<?> request : subscriptionManager.getAll()) {

			provider.subscribe(request.getTopic(), request.getQos());

		}

		log.info("Restored {} MQTT subscription(s).", subscriptionManager.size());

	}

	/**
	 * Returns current connection state.
	 */
	public ConnectionState getConnectionState() {

		return state.get();

	}

	/**
	 * Returns true when connected.
	 */
	public boolean isConnected() {

		return provider.isConnected();

	}

}