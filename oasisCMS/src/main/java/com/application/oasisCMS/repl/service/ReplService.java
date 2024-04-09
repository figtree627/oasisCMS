package com.application.oasisCMS.repl.service;

import java.util.List; 
import java.util.Map;

import com.application.oasisCMS.repl.dto.ReplDTO;

public interface ReplService {

	public void createRepl(Map<String,Object> createReplMap); 
	public List<Map<String,Object>> getReplList(long bdId);
	public ReplDTO getReplDetail(long replId);
	public int getReplCnt(long bdId);  
	public void updateRepl(ReplDTO replDTO);
	public void deleteRepl(long replId);
}

