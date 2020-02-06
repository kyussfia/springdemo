package com.example.demo.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoLogoutSuccessHandler implements LogoutSuccessHandler {

	private final String redirectUrl;

	public DemoLogoutSuccessHandler(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		response.sendRedirect(this.redirectUrl);
	}
}
