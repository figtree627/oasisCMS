package com.application.oasisCMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.oasisCMS.bs.service.BsService;

@Controller
public class MainController {
	
	@GetMapping
	public String a() {
		return "redirect:/main";
	}
	@GetMapping("/main")
	public String main() {
		return "index";
	}
	
	
	@GetMapping("/bd")
	public String bmain() {
		return "bd/main";
	}
	
	@GetMapping("/bs")
	public String bsmain() {
		return "bs/main";
	}
	
	
	
}
