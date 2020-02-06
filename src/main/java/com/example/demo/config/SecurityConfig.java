package com.example.demo.config;

import com.example.demo.captcha.CaptchaService;
import com.example.demo.filter.CaptchaAuthenticationFilter;
import com.example.demo.handler.DemoAuthenticationFailureHandler;
import com.example.demo.handler.DemoAuthenticationSuccessHandler;
import com.example.demo.handler.DemoLogoutSuccessHandler;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	private final UserRepository userRepository;

	private final GroupRepository groupRepository;

	private final CaptchaService captchaService;

	@Value("${app.security.attempts-before-captcha:3}")
	private Integer attemptsBeforeCaptcha;

	public SecurityConfig(UserRepository userRepository, GroupRepository groupRepository, CaptchaService captchaService) {
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.captchaService = captchaService;

	}

	@Override
	public void configure(WebSecurity web) {
		web
				.ignoring()
				.antMatchers(
						"/resources/**",
						"/webjars/**",
						"/static/**",
						"/css/**",
						"/images/**"
				);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/login**").permitAll()
					.antMatchers("/registration").permitAll()
					.antMatchers("/logoutSuccess").permitAll()
					.antMatchers("/favicon.ico").permitAll()
					.antMatchers("/admin/**").hasAuthority("ADMIN")
					.antMatchers("/editor/**").hasAnyAuthority("ADMIN", "EDITOR")
					.antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
					.anyRequest().authenticated()
				.and()
					.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessHandler(new DemoLogoutSuccessHandler("/logoutSuccess"))
				.and()
					.exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
		;
	}

	@Bean
	public CaptchaAuthenticationFilter authenticationFilter() throws Exception {
		CaptchaAuthenticationFilter authenticationFilter = new CaptchaAuthenticationFilter(this.userRepository, this.captchaService, this.attemptsBeforeCaptcha);
		authenticationFilter.setAuthenticationSuccessHandler(new DemoAuthenticationSuccessHandler(this.userRepository, "/index"));
		authenticationFilter.setAuthenticationFailureHandler(new DemoAuthenticationFailureHandler(this.userRepository, "/loginError", this.attemptsBeforeCaptcha));
		authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService(this.userRepository, this.groupRepository, passwordEncoder());
	}
}
