package com.example.demo.captcha;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

public class CaptchaServiceTest {

	private static final Integer TEST_CAPTCHA_LENGTH = 6;

	private static final String TEST_CASE_MODE = "UPPERCASE";

	private CaptchaService service;

	@Before
	public void setUp() {
		this.service = new CaptchaService(TEST_CAPTCHA_LENGTH, TEST_CASE_MODE);
	}

	@Test
	public void testCreate() {
		HttpSession dummySession = new MockHttpSession();
		final String base64ImageString = service.create(dummySession);
		Assert.assertNotNull(base64ImageString);
		Assert.assertFalse(base64ImageString.isEmpty());
		Assert.assertTrue(base64ImageString.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$"));
	}

	@Test
	public void testLoad() {
		HttpSession dummySession = new MockHttpSession();
		service.create(dummySession);
		final String loaded = service.load(dummySession);
		Assert.assertNotNull(loaded);
		Assert.assertFalse(loaded.isEmpty());
		Assert.assertEquals(loaded.length(), (int) TEST_CAPTCHA_LENGTH);
		for (char c : loaded.toCharArray()) {
			if (TEST_CASE_MODE.toLowerCase().equals("uppercase")) {
				Assert.assertTrue(Character.isUpperCase(c) || Character.isDigit(c));
			} else if (TEST_CASE_MODE.toLowerCase().equals("lowercase")) {
				Assert.assertTrue(Character.isLowerCase(c) || Character.isDigit(c));
			} else {
				Assert.assertTrue(Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c));
			}
		}
	}
}
