package com.talkovia.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.talkovia.dto.ForgotRequestDTO;

@Service
public class EmailService {
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	public void sendEmail(ForgotRequestDTO email) {
		var message= new SimpleMailMessage();
		message.setFrom("noreply@email.com");
		message.setTo(email.email());
		message.setSubject("recupeção de senha");
		message.setText("por favor, recupere sua senha nesse link");
		mailSender.send(message);
	}
}
