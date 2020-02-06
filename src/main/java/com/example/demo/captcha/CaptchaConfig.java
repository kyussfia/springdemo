package com.example.demo.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {

	@Value("${app.security.captcha.max-length:4}")
	private Integer captchaLength = 4;

	@Value("${app.security.captcha.case-mode:UPPERCASE}")
	private String captchaCaseMode;

	@Bean
	public CaptchaService captchaService() {
		return new CaptchaService(this.captchaLength, this.captchaCaseMode);
	}
}