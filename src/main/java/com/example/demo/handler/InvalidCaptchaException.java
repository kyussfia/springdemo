package com.example.demo.handler;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidCaptchaException extends BadCredentialsException {
	public InvalidCaptchaException(String msg) {
		super(msg);
	}
}
