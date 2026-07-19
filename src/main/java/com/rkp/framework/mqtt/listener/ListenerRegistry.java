package com.rkp.framework.mqtt.listener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Stores all registered MQTT listeners.
 */
public class ListenerRegistry {

	private final Map<String, CopyOnWriteArrayList<ListenerRegistration<?>>> registry = new ConcurrentHashMap<>();

	public void register(String topic, ListenerRegistration<?> registration) {

		registry.computeIfAbsent(topic, key -> new CopyOnWriteArrayList<>()).add(registration);

	}

	public List<ListenerRegistration<?>> getListeners(String topic) {

		return List.copyOf(registry.getOrDefault(topic, new CopyOnWriteArrayList<>()));

	}

	public boolean isRegistered(String topic) {

		return registry.containsKey(topic);

	}

	public void remove(String topic) {

		registry.remove(topic);

	}

	public void clear() {

		registry.clear();

	}

	public int size() {

		return registry.size();

	}

}