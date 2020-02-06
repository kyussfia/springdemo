package com.example.demo.captcha;

import com.github.cage.Cage;
import com.github.cage.YCage;

import javax.servlet.http.HttpSession;
import java.util.Base64;

public class CaptchaService {

	private static final String sessionKey = "captcha";

	private static final String UPPERCASE = "UPPERCASE";

	private static final String MIXED = "MIXED";

	private static final String LOWERCASE = "LOWERCASE";

	private final Integer captchaLength;

	private final Cage cage = new YCage();

	private String caseMode; //todo later to enum

	CaptchaService(Integer captchaLength, String caseMode) {
		this.captchaLength = captchaLength;
		this.initCaseMode(caseMode);
	}

	private void initCaseMode(String caseMode) {
		switch (caseMode.toUpperCase()) {
			case UPPERCASE:
				this.caseMode = UPPERCASE;
				break;
			case MIXED:
				this.caseMode = MIXED;
				break;
			case LOWERCASE:
				this.caseMode = LOWERCASE;
				break;
		}
	}

	private String generateToken() {
		String token = this.cage.getTokenGenerator().next();
		String result = token;
		if (token.length() > this.captchaLength) {
			result = token.substring(0, this.captchaLength);
		}
		return this.applyCaseMode(result);
	}

	private String applyCaseMode(String token) {
		if (this.caseMode.equals(UPPERCASE)) {
			return token.toUpperCase();
		}
		if (this.caseMode.equals(LOWERCASE)) {
			return token.toLowerCase();
		}
		return token;
	}

	private byte[] getFileWithToken(String token) {
		return this.cage.draw(token);
	}

	private String imageToBase64String(final byte[] image) {
		return Base64.getEncoder().encodeToString(image);
	}

	public String create(HttpSession session) {
		String token = this.generateToken();
		session.setAttribute(sessionKey, token);
		return this.imageToBase64String(this.getFileWithToken(token));
	}

	public String load(HttpSession session) {
		return session.getAttribute(sessionKey).toString();
	}
}
