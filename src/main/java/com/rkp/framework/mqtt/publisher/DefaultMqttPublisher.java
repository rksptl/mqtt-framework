package com.rkp.framework.mqtt.publisher;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rkp.framework.mqtt.converter.JsonMessageConverter;
import com.rkp.framework.mqtt.exception.PublishException;
import com.rkp.framework.mqtt.model.PublishRequest;
import com.rkp.framework.mqtt.spi.MqttClientProvider;
import com.rkp.framework.mqtt.validation.QosValidator;
import com.rkp.framework.mqtt.validation.TopicValidator;

/**
 * Default implementation of {@link MqttPublisher}.
 */
public class DefaultMqttPublisher implements MqttPublisher {

	private static final Logger log = LoggerFactory.getLogger(DefaultMqttPublisher.class);

	private final MqttClientProvider provider;

	private final JsonMessageConverter converter;

	private final TopicValidator topicValidator;

	private final QosValidator qosValidator;

	public DefaultMqttPublisher(MqttClientProvider provider, JsonMessageConverter converter,
			TopicValidator topicValidator, QosValidator qosValidator) {

		this.provider = Objects.requireNonNull(provider);
		this.converter = Objects.requireNonNull(converter);
		this.topicValidator = Objects.requireNonNull(topicValidator);
		this.qosValidator = Objects.requireNonNull(qosValidator);

	}

	@Override
	public void publish(PublishRequest request) {

		Objects.requireNonNull(request, "PublishRequest must not be null.");

		topicValidator.validate(request.getTopic());
		qosValidator.validate(request.getQos());

		String payload = converter.toJson(request.getPayload());

		log.debug("Publishing MQTT message to topic '{}', qos={}, retained={}", request.getTopic(), request.getQos(),
				request.isRetained());

		try {

			provider.publish(request.getTopic(), payload, request.getQos(), request.isRetained());

		} catch (Exception ex) {

			throw new PublishException("Failed to publish MQTT message to topic: " + request.getTopic(), ex);

		}

	}

}