package com.bridgelabz.fundo.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundo.user.dto.EmailIdDto;

@Component
@PropertySource("classpath:application.properties")
public class MessageProducer {

	@Autowired
	private AmqpTemplate amqpTemplate;	
	private String exchangeName = "messageExchange";
	private String routingKey = "messageKey";

	public void sendMessage(EmailIdDto message)
	{
		//System.out.println(message);
		//System.out.println("in rabbitmq");
		try {
			amqpTemplate.convertAndSend(exchangeName, routingKey, message);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
