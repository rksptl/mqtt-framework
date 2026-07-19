package com.rkp.framework.mqtt.subscriber;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rkp.framework.mqtt.model.SubscribeRequest;

/**
 * Stores active subscriptions.
 *
 * Used to restore subscriptions after reconnect.
 */
public class SubscriptionManager {

	private final Map<String, SubscribeRequest<?>> subscriptions = new ConcurrentHashMap<>();

	/**
	 * Store subscription.
	 */
	public void add(SubscribeRequest<?> request) {

		subscriptions.put(request.getTopic(), request);

	}

	/**
	 * Remove subscription.
	 */
	public void remove(String topic) {

		subscriptions.remove(topic);

	}

	/**
	 * Return subscription.
	 */
	public SubscribeRequest<?> get(String topic) {

		return subscriptions.get(topic);

	}

	/**
	 * Return all subscriptions.
	 */
	public Collection<SubscribeRequest<?>> getAll() {

		return subscriptions.values();

	}

	/**
	 * Clear subscriptions.
	 */
	public void clear() {

		subscriptions.clear();

	}

	/**
	 * Number of subscriptions.
	 */
	public int size() {

		return subscriptions.size();

	}

}