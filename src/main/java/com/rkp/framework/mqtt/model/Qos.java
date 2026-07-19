package com.rkp.framework.mqtt.model;

/**
 * MQTT Quality of Service levels.
 */
public enum Qos {

	/**
	 * At most once delivery.
	 */
	AT_MOST_ONCE(0),

	/**
	 * At least once delivery.
	 */
	AT_LEAST_ONCE(1),

	/**
	 * Exactly once delivery.
	 */
	EXACTLY_ONCE(2);

	private final int value;

	Qos(int value) {
		this.value = value;
	}

	/**
	 * Returns the MQTT QoS numeric value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Creates a QoS enum from an integer value.
	 *
	 * @param value MQTT QoS value
	 * @return matching enum
	 */
	public static Qos fromValue(int value) {

		for (Qos qos : values()) {

			if (qos.value == value) {
				return qos;
			}

		}

		throw new IllegalArgumentException("Invalid MQTT QoS: " + value);

	}

}