package com.rkp.framework.mqtt.subscriber;

import java.util.Objects;

import com.rkp.framework.mqtt.listener.LambdaMessageHandler;
import com.rkp.framework.mqtt.listener.ListenerRegistration;
import com.rkp.framework.mqtt.listener.ListenerRegistry;
import com.rkp.framework.mqtt.model.SubscribeRequest;
import com.rkp.framework.mqtt.spi.MqttClientProvider;
import com.rkp.framework.mqtt.validation.QosValidator;
import com.rkp.framework.mqtt.validation.TopicValidator;

/**
 * Default implementation of {@link MqttSubscriber}.
 */
public class DefaultMqttSubscriber implements MqttSubscriber {

	private final ListenerRegistry registry;

	private final SubscriptionManager manager;

	private final MqttClientProvider provider;

	private final TopicValidator topicValidator;

	private final QosValidator qosValidator;

	public DefaultMqttSubscriber(ListenerRegistry registry, SubscriptionManager manager, MqttClientProvider provider,
			TopicValidator topicValidator, QosValidator qosValidator) {

		this.registry = Objects.requireNonNull(registry);
		this.manager = Objects.requireNonNull(manager);
		this.provider = Objects.requireNonNull(provider);
		this.topicValidator = Objects.requireNonNull(topicValidator);
		this.qosValidator = Objects.requireNonNull(qosValidator);

	}

	@Override
	public <T> void subscribe(SubscribeRequest<T> request) {

		Objects.requireNonNull(request, "SubscribeRequest must not be null.");

		topicValidator.validate(request.getTopic());

		qosValidator.validate(request.getQos());

		ListenerRegistration<T> registration = new ListenerRegistration<>(request.getMessageType(),
				new LambdaMessageHandler<>(request.getListener()));

		registry.register(request.getTopic(), registration);

		manager.add(request);

		if (provider.isConnected()) {
		    provider.subscribe(request.getTopic(), request.getQos());
		}
	}

	@Override
	public void unsubscribe(String topic) {

		topicValidator.validate(topic);

		provider.unsubscribe(topic);

		registry.remove(topic);

		manager.remove(topic);

	}

}