package com.rkp.framework.mqtt.listener;

import java.util.Objects;

/**
 * Immutable listener registration.
 *
 * @param <T> payload type
 */
public final class ListenerRegistration<T> {

	private final Class<T> payloadType;

	private final MessageHandler<T> handler;

	public ListenerRegistration(Class<T> payloadType, MessageHandler<T> handler) {

		this.payloadType = Objects.requireNonNull(payloadType);
		this.handler = Objects.requireNonNull(handler);

	}

	public Class<T> getPayloadType() {
		return payloadType;
	}

	public MessageHandler<T> getHandler() {
		return handler;
	}

}