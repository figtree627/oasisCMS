package com.application.oasisCMS.bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.oasisCMS.bs.service.BsService;

@Controller
@RequestMapping("/bs")
public class BsController {
	
	@Autowired
	private BsService bsService;
	
	
	
	@GetMapping("/main")
	public String main() {
		return "bs/main";
	}
}
