package com.rkp.framework.mqtt.listener;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rkp.framework.mqtt.converter.JsonMessageConverter;

/**
 * Dispatches MQTT messages to registered listeners.
 */
public class MessageDispatcher {

	private static final Logger log = LoggerFactory.getLogger(MessageDispatcher.class);

	private final ListenerRegistry registry;

	private final JsonMessageConverter converter;

	public MessageDispatcher(ListenerRegistry registry, JsonMessageConverter converter) {

		this.registry = registry;
		this.converter = converter;

	}

	public void dispatch(String topic, byte[] payload) {

		dispatch(topic, new String(payload, StandardCharsets.UTF_8));

	}

	@SuppressWarnings("unchecked")
	public void dispatch(String topic, String json) {

		List<ListenerRegistration<?>> listeners = registry.getListeners(topic);

		for (ListenerRegistration<?> registration : listeners) {

			try {

				Object object = converter.fromJson(json, registration.getPayloadType());

				((MessageHandler<Object>) registration.getHandler()).handle(topic, object);

			} catch (Exception ex) {

				log.error("Failed to dispatch MQTT message for topic '{}'", topic, ex);

			}

		}

	}

}