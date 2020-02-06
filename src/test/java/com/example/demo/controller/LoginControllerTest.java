package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest extends AbstractControllerTest {

	@Test
	public void testLoginGet() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testLoginGetWithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testLoginPost() throws Exception {
		mvc.perform(post("/login"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/loginError?"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testLoginPostWithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testLoginGet2() throws Exception {
		mvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testLoginGet2WithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testLoginError() throws Exception {
		mvc.perform(get("/loginError"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("login"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testLoginErrorWithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testLogoutSuccess() throws Exception {
		mvc.perform(get("/logoutSuccess"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("login"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testLogoutSuccessWithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testRegistrationGet() throws Exception {
		mvc.perform(get("/registration"))
				.andExpect(status().isOk())
				.andExpect(view().name("registration"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testRegistrationGetWithUser() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}

	@Test
	public void testRegistrationPost() throws Exception {
		mvc.perform(post("/registration").queryParam("username", "testuser").queryParam("password", "testtest"))
				.andExpect(status().isOk())
				.andExpect(view().name("registration"))
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testRegistrationPostWithUSer() throws Exception {
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/index"))
				.andReturn();
	}
}
