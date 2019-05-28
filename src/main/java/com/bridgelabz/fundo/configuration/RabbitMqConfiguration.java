package com.bridgelabz.fundo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchangeName;

	@Value("${spring.rabbitmq.template.default-receive-queue}")
	private String queueName;

	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;

	@Bean
	public Exchange mailExchange() {
		return new DirectExchange(exchangeName);
	}

	/* Creating a bean for the Message queue */
	@Bean
	public Queue mailQueue() {
		return new Queue(queueName);
	}

	@Bean
	public Binding declareBinding(Queue messageQueue, DirectExchange messageExchange) {
		return BindingBuilder.bind(messageQueue).to(messageExchange).with(routingKey);
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
