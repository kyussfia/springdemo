package com.example.demo.handler;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class DemoAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	private final UserRepository userRepository;

	private final String redirectUrl;

	public DemoAuthenticationSuccessHandler(UserRepository userRepository, String redirectUrl) {
		this.userRepository = userRepository;
		this.redirectUrl = redirectUrl;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		User loggedIn = (User) authentication.getPrincipal();
		loggedIn.setLastLogin(new Date(System.currentTimeMillis()));
		loggedIn.setLoginFailures(0);
		this.userRepository.save(loggedIn);
		response.sendRedirect(this.redirectUrl);
	}
}
