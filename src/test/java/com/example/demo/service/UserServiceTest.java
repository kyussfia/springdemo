package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private GroupRepository mockGroupRepository;

	@Mock
	private PasswordEncoder mockPasswordEncoder;

	private UserService service;

	@Before
	public void setUp() {
		initMocks(this);
		service = new UserService(
				mockUserRepository,
				mockGroupRepository,
				mockPasswordEncoder
		);

		User user = new User();
		user.setUsername("testuser");
		user.setLastLogin(new Date(System.currentTimeMillis()));
		user.setLoginFailures(0);
		user.setPassword("qwertz");

		Mockito.when(mockUserRepository.save(any()))
				.thenReturn(user);
		Mockito.when(mockUserRepository.findByUsername(eq("testuser"))).thenReturn(user);
		Mockito.when(mockUserRepository.findByUsername(not(eq("testuser")))).thenReturn(null);
	}

	@Test
	public void testFindUserByUsername() {
		final String name = "testuser";
		final User result = service.findByUsername(name);
		Assert.assertEquals(name, result.getUsername());
	}

	@Test
	public void testFindUserByUsernameNotFound() {
		final String name = "testtest";
		final User result = service.findByUsername(name);
		Assert.assertNull(result);
	}

	@Test
	public void testLoadUserByUsername() {
		final String name = "testuser";
		final UserDetails result = service.loadUserByUsername(name);
		Assert.assertEquals(name, result.getUsername());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNotFound() {
		final String name = "testtest";
		service.loadUserByUsername(name);
	}

	@Test
	public void testSaveUser() {
		final String name = "testuser";
		final User user = service.saveUser(new User());
		Assert.assertEquals(name, user.getUsername());
	}
}
