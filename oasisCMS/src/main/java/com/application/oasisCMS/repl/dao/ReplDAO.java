package com.application.oasisCMS.repl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.repl.dto.ReplDTO;

@Mapper
public interface ReplDAO {

	public void createRepl(ReplDTO replDTO);
	public int getReplCnt(long boardId);
	public List<ReplDTO> getReplList(long boardId);
	public ReplDTO getReplDetail(long replId);
	public void updateRepl(ReplDTO replDTO);
	public void deleteRepl(long replId);
	public String validateReplUserCheck(long replId);
}
