package com.application.oasisCMS.author.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.author.dto.AuthorDTO;

@Mapper
public interface AuthorDAO {
	public void createBd(AuthorDTO authorDTO);
	public List<AuthorDTO> getBdList();
	public AuthorDTO getBdDetail(long bdId);
	public void updateReadCnt(long bdId);
	public void updateBd(AuthorDTO authorDTO);
	public void deleteBd(long bdId);
}
