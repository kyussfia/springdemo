package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest extends AbstractControllerTest {

	@Test
	public void testIndex() throws Exception {
		mvc.perform(get("/index"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@WithMockUser
	@Test
	public void testIndexWithUser() throws Exception {
		mvc.perform(get("/index"))
				.andExpect(status().isOk())
				//.andExpect(view().name("index"))
				.andReturn();
	}

	@Test
	public void testFavicon() throws Exception {
		mvc.perform(get("/favicon.ico"))
				.andExpect(status().isOk())
				.andExpect(content().string(""))
				.andReturn();
	}
}
