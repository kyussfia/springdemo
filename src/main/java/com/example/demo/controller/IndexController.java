package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@GetMapping("/index")
	public ModelAndView index(){
		return new ModelAndView("index");
	}

	@SuppressWarnings("unused")
	@GetMapping("favicon.ico")
	@ResponseBody
	void returnNoFavicon() {}
}
