package com.goKart.goKart.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

@SpringBootTest
class EmailControllerTest {

	@InjectMocks
	EmailController	emailController;

	@Test
	void test() {
		emailController.sendMail();
		SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hello from Spring Boot Application");
        message.setTo("contatoleonardopires@gmail.com");
        message.setFrom("leonardopires-1997@hotmail.com");
	}

}
