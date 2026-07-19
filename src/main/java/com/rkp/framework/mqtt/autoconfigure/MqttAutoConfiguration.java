package com.rkp.framework.mqtt.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.rkp.framework.mqtt.config.MqttProperties;
import com.rkp.framework.mqtt.converter.JsonMessageConverter;
import com.rkp.framework.mqtt.lifecycle.MqttLifecycleManager;
import com.rkp.framework.mqtt.listener.ListenerRegistry;
import com.rkp.framework.mqtt.listener.MessageDispatcher;
import com.rkp.framework.mqtt.publisher.DefaultMqttPublisher;
import com.rkp.framework.mqtt.publisher.MqttPublisher;
import com.rkp.framework.mqtt.spi.MqttClientProvider;
import com.rkp.framework.mqtt.spi.PahoMqttClientProvider;
import com.rkp.framework.mqtt.subscriber.DefaultMqttSubscriber;
import com.rkp.framework.mqtt.subscriber.MqttSubscriber;
import com.rkp.framework.mqtt.subscriber.SubscriptionManager;
import com.rkp.framework.mqtt.validation.BrokerUrlValidator;
import com.rkp.framework.mqtt.validation.ClientIdValidator;
import com.rkp.framework.mqtt.validation.QosValidator;
import com.rkp.framework.mqtt.validation.TopicValidator;

@AutoConfiguration
@EnableConfigurationProperties(MqttProperties.class)
@ConditionalOnProperty(prefix = "mqtt", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MqttAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	TopicValidator topicValidator() {
		return new TopicValidator();
	}

	@Bean
	@ConditionalOnMissingBean
	QosValidator qosValidator() {
		return new QosValidator();
	}

	@Bean
	@ConditionalOnMissingBean
	ClientIdValidator clientIdValidator() {
		return new ClientIdValidator();
	}

	@Bean
	@ConditionalOnMissingBean
	BrokerUrlValidator brokerUrlValidator() {
		return new BrokerUrlValidator();
	}

	@Bean
	@ConditionalOnMissingBean
	ListenerRegistry listenerRegistry() {
		return new ListenerRegistry();
	}

	@Bean
	@ConditionalOnMissingBean
	SubscriptionManager subscriptionManager() {
		return new SubscriptionManager();
	}

	@Bean
	@ConditionalOnMissingBean
	MessageDispatcher messageDispatcher(ListenerRegistry registry, JsonMessageConverter converter) {

		return new MessageDispatcher(registry, converter);
	}

	@Bean
	@ConditionalOnMissingBean
	MqttClientProvider mqttClientProvider(MqttProperties properties, MessageDispatcher dispatcher,
			BrokerUrlValidator brokerValidator, ClientIdValidator clientValidator) {

		return new PahoMqttClientProvider(properties, dispatcher, brokerValidator, clientValidator);
	}

	@Bean
	@ConditionalOnMissingBean
	MqttPublisher mqttPublisher(MqttClientProvider provider, JsonMessageConverter converter,
			TopicValidator topicValidator, QosValidator qosValidator) {

		return new DefaultMqttPublisher(provider, converter, topicValidator, qosValidator);
	}

	@Bean
	@ConditionalOnMissingBean
	MqttSubscriber mqttSubscriber(ListenerRegistry registry, SubscriptionManager manager, MqttClientProvider provider,
			TopicValidator topicValidator, QosValidator qosValidator) {

		return new DefaultMqttSubscriber(registry, manager, provider, topicValidator, qosValidator);
	}

	@Bean
	@ConditionalOnMissingBean
	MqttLifecycleManager mqttLifecycleManager(MqttClientProvider provider, SubscriptionManager manager) {

		return new MqttLifecycleManager(provider, manager);
	}

	@Bean
	ApplicationRunner mqttApplicationRunner(MqttLifecycleManager lifecycleManager) {

		return args -> lifecycleManager.start();
	}

	@Bean
	DisposableBean mqttShutdown(MqttLifecycleManager lifecycleManager) {

		return lifecycleManager::stop;
	}

	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper() {
		return JsonMapper.builder().findAndAddModules().build();
	}

	@Bean
	@ConditionalOnMissingBean
	public JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {

		return new JsonMessageConverter(objectMapper);
	}

}