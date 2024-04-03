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
	
	
	@GetMapping("/bs")
	public String bsmain() {
		return "bs/main";
	}
	@GetMapping("/book")
	public String bookMain() {
		return "book/main";
	}
	
	@GetMapping("/author")
	public String authorMain() {
		return "book/author";
	}
		
}
