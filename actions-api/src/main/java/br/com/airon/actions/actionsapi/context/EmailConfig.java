package br.com.airon.actions.actionsapi.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Class to configure the JavaMailSender
 * @author airon
 *
 */
@Configuration
public class EmailConfig {

	@Bean
	public JavaMailSenderImpl getEmailBean() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(""); // put here the host
		mailSender.setUsername(""); // put here the username
		mailSender.setPassword(""); // put here the password
		
		mailSender.getJavaMailProperties().put("mail.smtp.auth", true);
		
		return mailSender;
	}

}
