package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final GroupRepository groupRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(
			UserRepository userRepository,
			GroupRepository groupRepository,
			PasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserDetails user =  this.findByUsername(userName);
		if (null == user) {
			throw new UsernameNotFoundException(userName);
		}
		return user;
	}

	public User findByUsername(String userName) {
		return this.userRepository.findByUsername(userName);
	}

	@SuppressWarnings("UnusedReturnValue")
	public User saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.addGroup(this.groupRepository.findByName("USER"));
		return userRepository.save(user);
	}
}
