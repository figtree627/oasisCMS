package com.application.oasisCMS.memorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memorial")
public class MemorialController {
	
	@GetMapping("/main")
	public String main() {
		return "memorial/main";
	}
}
