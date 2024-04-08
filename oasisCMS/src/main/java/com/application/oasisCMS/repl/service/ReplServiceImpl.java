package com.application.oasisCMS.repl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.oasisCMS.repl.dao.ReplDAO;
import com.application.oasisCMS.repl.dto.ReplDTO;

@Service

public class ReplServiceImpl implements ReplService {

	@Autowired
	private ReplDAO replDAO;


	public void createRepl(ReplDTO replDTO) {
		
	}
}
