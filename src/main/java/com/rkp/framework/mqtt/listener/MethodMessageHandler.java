package com.rkp.framework.mqtt.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.rkp.framework.mqtt.exception.MqttFrameworkException;

/**
 * Reflection based message handler. Used by future @MqttListener support.
 */
public class MethodMessageHandler<T> implements MessageHandler<T> {

	private final Object target;

	private final Method method;

	public MethodMessageHandler(Object target, Method method) {

		this.target = target;
		this.method = method;
		this.method.setAccessible(true);

	}

	@Override
	public void handle(String topic, T payload) {

		try {

			method.invoke(target, topic, payload);

		} catch (IllegalAccessException | InvocationTargetException ex) {

			throw new MqttFrameworkException("Failed to invoke MQTT listener method.", ex);

		}

	}

}