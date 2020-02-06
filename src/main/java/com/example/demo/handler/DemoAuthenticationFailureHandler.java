package com.example.demo.handler;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final UserRepository userRepository;

	private final String redirectUrl;

	private final Integer attemptsBeforeCaptcha;

	public DemoAuthenticationFailureHandler(UserRepository userRepository, String redirectUrl, Integer attemptsBeforeCaptcha) {
		this.userRepository = userRepository;
		this.redirectUrl = redirectUrl;
		//noinspection deprecation
		Assert.notNull(attemptsBeforeCaptcha);
		this.attemptsBeforeCaptcha = attemptsBeforeCaptcha;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		User user = this.userRepository.findByUsername(request.getParameter("username"));
		String redirectUrl = this.redirectUrl + "?";
		if (null != user) {
			user.setLoginFailures(user.getLoginFailures() + 1);
			this.userRepository.save(user);
			redirectUrl +=  "&captcha=" + user.haveToAnswerCaptcha(this.attemptsBeforeCaptcha);
		}
		response.sendRedirect(redirectUrl);
	}
}
