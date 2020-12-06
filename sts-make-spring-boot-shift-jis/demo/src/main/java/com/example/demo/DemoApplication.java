package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

	@Autowired
	private MessageSource messageSource;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//Log4j2�Ń��O�o�͂��s��
		Logger logger = LogManager.getLogger(DemoApplication.class);
		logger.debug("�����DEBUG(�f�o�b�O)�̃e�X�g�p���O�ł�");
		logger.info("�����INFO(���)�̃e�X�g�p���O�ł�");
		logger.warn("�����WARN(�x��)�̃e�X�g�p���O�ł�");
		logger.error("�����ERROR(�G���[)�̃e�X�g�p���O�ł��B");
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		//Spring Boot�f�t�H���g�̃G���[���b�Z�[�W�̃v���p�e�B�t�@�C����
		//ValidationMessages.properties����messages.properties�ɕύX����
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}

	@Override
	public org.springframework.validation.Validator getValidator() {
		return validator();
	}
}
