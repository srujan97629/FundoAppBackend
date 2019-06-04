package com.bridgelabz.fundo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundo.user.dto.EmailIdDto;
import com.bridgelabz.fundo.utility.EmailService;

@Component
public class Consumer{
	
	 @Autowired
	    private EmailService emailService;
	 
	@RabbitListener(queues="messageQueue")
	public void reciveMessage(EmailIdDto message)  {
		System.out.println("received msg : "+message);
		emailService.sendMail(message);
	}

}
