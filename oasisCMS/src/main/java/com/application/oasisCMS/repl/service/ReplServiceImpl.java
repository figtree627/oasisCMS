package com.application.oasisCMS.repl.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.oasisCMS.repl.dao.ReplDAO;
import com.application.oasisCMS.repl.dto.ReplDTO;

@Service

public class ReplServiceImpl implements ReplService {

	@Autowired
	private ReplDAO replDAO;

	// 리플 등록
	@Override
	public void createRepl(ReplDTO replDTO) {
		System.out.println(replDTO);
		replDAO.createRepl(replDTO);
	}

	// 리플 리스트 
	@Override
	public List<Map<String,Object>> getReplList(long bdId) {
		return replDAO.getReplList(bdId);
	}

	@Override
	public int getReplCnt(long bdId) {
		
		return replDAO.getReplCnt(bdId);
	}

	@Override
	public void updateRepl(ReplDTO replDTO) {
		System.out.println("리플 서비스 도착");
		replDAO.updateRepl(replDTO);
	}

	@Override
	public ReplDTO getReplDetail(long replId) {
		
		return replDAO.getReplDetail(replId);
	}
}
