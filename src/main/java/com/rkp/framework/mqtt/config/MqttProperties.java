package com.rkp.framework.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for MQTT.
 */
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

	/**
	 * Enable or disable MQTT framework.
	 */
	private boolean enabled = true;

	/**
	 * MQTT broker URL.
	 */
	private String brokerUrl = "tcp://localhost:1883";

	/**
	 * MQTT client identifier.
	 */
	private String clientId = "mqtt-client";

	private String username;

	private String password;

	private boolean cleanSession = true;

	private boolean automaticReconnect = true;

	private int connectionTimeout = 30;

	private int keepAliveInterval = 60;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCleanSession() {
		return cleanSession;
	}

	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	public boolean isAutomaticReconnect() {
		return automaticReconnect;
	}

	public void setAutomaticReconnect(boolean automaticReconnect) {
		this.automaticReconnect = automaticReconnect;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getKeepAliveInterval() {
		return keepAliveInterval;
	}

	public void setKeepAliveInterval(int keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}
}