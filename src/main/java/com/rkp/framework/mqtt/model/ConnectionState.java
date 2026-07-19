package com.rkp.framework.mqtt.model;

/**
 * Represents the lifecycle state of the MQTT connection.
 */
public enum ConnectionState {

	DISCONNECTED,

	CONNECTING,

	CONNECTED,

	RECONNECTING,

	CLOSED

}