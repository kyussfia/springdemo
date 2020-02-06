package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractControllerTest {
	@Test
	public void testHome() throws Exception {
		mvc.perform(get("/user/home"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void testHomeWithNonEditor() throws Exception {
		mvc.perform(get("/user/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/home"))
				.andReturn();
	}

	@WithMockUser(username = "editor", authorities = {"EDITOR"})
	@Test
	public void testHomeWithEditor() throws Exception {
		mvc.perform(get("/user/home"))
				.andExpect(status().isForbidden())
				.andReturn();
	}

	@WithMockUser(username = "admin", authorities = {"ADMIN"})
	@Test
	public void testHomeWithAdmin() throws Exception {
		mvc.perform(get("/user/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/home"))
				.andReturn();
	}
}
