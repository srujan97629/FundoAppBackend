package com.bridgelabz.fundo.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.user.dto.EmailIdDto;
import com.bridgelabz.fundo.utility.TokenGenerator;

@Service
public class EmailService
{
     private JavaMailSender javaMailSender;
        
     
     @Autowired
	public EmailService(JavaMailSender javaMailSender)
	{		
		this.javaMailSender = javaMailSender;
	}
     @Autowired
     private TokenGenerator tokenGenerator;
     
     public void sendMail(EmailIdDto emailId)
     {
    	 System.out.println("Mail sending");
    	 SimpleMailMessage mail=new SimpleMailMessage();
    	 
    	 mail.setTo(emailId.getTo());
    	 
    	 mail.setSubject(emailId.getSubject());
    	 
    	 mail.setText(emailId.getBody());
    	    	
    	 javaMailSender.send(mail);
    	 System.out.println("mail sent");
     }
     public String getLink(String link,long id) throws UnsupportedEncodingException
     {
    	 return link+"/"+tokenGenerator.generateToken(id);
     }
     
}





