package com.example.demo.controller;

import com.example.demo.captcha.CaptchaService;
import com.example.demo.config.SecurityConfig;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

abstract class AbstractControllerTest {

	@MockBean
	protected UserRepository userRepository;

	@MockBean
	protected GroupRepository groupRepository;

	@MockBean
	protected CaptchaService captchaService;

	@Autowired
	protected SecurityConfig securityConfig;

	@Autowired
	protected MockMvc mvc;
}
