package com.application.oasisCMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.application.oasisCMS.repl.controller.ReplController;
import com.application.oasisCMS.repl.service.ReplService;

@SpringBootTest
public class ReplTest {
	
	@Autowired
	private ReplService replService;
	
	@Autowired ReplController replController;
	
	@Test
	public void ex01(){
//		replController.ex01(long replId);
		
		
		
	}
}
