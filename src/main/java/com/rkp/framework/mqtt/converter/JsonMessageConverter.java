package com.rkp.framework.mqtt.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkp.framework.mqtt.exception.DeserializationException;
import com.rkp.framework.mqtt.exception.SerializationException;

/**
 * Converts Java objects to JSON and JSON to Java objects.
 *
 * <p>
 * This class is thread-safe because Jackson's ObjectMapper is thread-safe after
 * configuration.
 * </p>
 */
public class JsonMessageConverter {

	private final ObjectMapper objectMapper;

	/**
	 * Creates a new converter.
	 *
	 * @param objectMapper Jackson ObjectMapper
	 */
	public JsonMessageConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Converts a Java object into JSON.
	 *
	 * @param payload Java object
	 * @return JSON string
	 * @throws SerializationException if serialization fails
	 */
	public String toJson(Object payload) {

		if (payload == null) {
			throw new SerializationException("Payload must not be null.");
		}

		try {

			return objectMapper.writeValueAsString(payload);

		} catch (JsonProcessingException ex) {

			throw new SerializationException("Failed to serialize payload of type " + payload.getClass().getName(), ex);

		}

	}

	/**
	 * Converts a JSON payload into the specified Java object.
	 *
	 * @param payload JSON payload
	 * @param type target class
	 * @param <T> target type
	 * @return deserialized object
	 */
	public <T> T fromJson(String payload, Class<T> type) {

		if (type == String.class) {
			return type.cast(payload);
		}

		try {
			return objectMapper.readValue(payload, type);
		} catch (Exception ex) {
			throw new DeserializationException("Failed to deserialize JSON into " + type.getName(), ex);
		}
	}

}