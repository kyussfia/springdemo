package com.example.demo.controller;

import com.example.demo.captcha.CaptchaService;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

	private final UserService userService;

	private final CaptchaService captchaService;

	LoginController(UserDetailsService userDetailsService, CaptchaService captchaService) {
		this.userService = (UserService) userDetailsService;
		this.captchaService = captchaService;
	}

	@GetMapping(value={"/", "/login"})
	public ModelAndView login() {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("forward:/index");
		}
		return new ModelAndView("login");
	}

	@GetMapping(value={"/loginError"})
	public RedirectView loginError(
			HttpSession session,
			RedirectAttributes model,
			@RequestParam(value = "captcha", required = false) Boolean captcha
	) {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
				return new RedirectView("index");
		}
		model.addFlashAttribute("captcha", null != captcha && captcha);
		model.addFlashAttribute("captchaPicture", this.captchaService.create(session));
		model.addFlashAttribute("failure", true);
		return new RedirectView("login");
	}

	@GetMapping(value={"/logoutSuccess"})
	public RedirectView logoutSuccess(RedirectAttributes model) {
		model.addFlashAttribute("logout", true);
		return new RedirectView("login");
	}

	@GetMapping(value="/registration")
	public ModelAndView registration() {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("forward:/index");
		}
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("forward:/index");
		}
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findByUsername(user.getUsername());
		if (userExists != null) {
			bindingResult
					.rejectValue("username", "error.user",
							"There is already a user registered with the user name provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("success", true);
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}
}
