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
@WebMvcTest(AdminController.class)
public class AdminControllerTest extends AbstractControllerTest {

	@Test
	public void testHome() throws Exception {
		mvc.perform(get("/admin/home"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@WithMockUser(username = "nonadmin", authorities = {"USER"})
	@Test
	public void testHomeWithNonAdmin() throws Exception {
		mvc.perform(get("/admin/home"))
				.andExpect(status().isForbidden())
				.andReturn();
	}

	@WithMockUser(username = "admin", authorities = {"ADMIN"})
	@Test
	public void testHomeWithAdmin() throws Exception {
		mvc.perform(get("/admin/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/home"))
				.andReturn();
	}
}
