package com.create.MyOwnValidators;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MyOwnValidatorsApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(MyOwnValidatorsApplication.class, args);

		EmailMessage message = new EmailMessage(
				"abc@abc.com",
				"xyz@xyz.com",
				"Hejo! Co tam kurka u Ciebie słychać wariacie? Pozdro"
		);

		EmailService emailService = context.getBean(EmailService.class);

		emailService.sendMessage(message);
	}

}
