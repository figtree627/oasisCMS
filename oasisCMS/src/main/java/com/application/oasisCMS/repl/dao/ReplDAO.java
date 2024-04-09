package com.application.oasisCMS.repl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.repl.dto.ReplDTO;

@Mapper
public interface ReplDAO {

	public void createRepl(Map<String,Object> createReplMap);
	public int getReplCnt(long bdId);
	public List<Map<String,Object>> getReplList(long bdId);
	// bdId, content, createDT,memberNick, profileIMg
	
	// 보드 DTO 하나 , 리플 리스트 여러개
	
	
	
	// 디테일 -  
	public ReplDTO getReplDetail(long replId);
	
	
	public void updateRepl(ReplDTO replDTO);
	public void deleteRepl(long replId);
//	public String validateReplUserCheck(long replId);
}
