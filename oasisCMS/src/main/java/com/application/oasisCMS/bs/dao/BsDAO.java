package com.application.oasisCMS.bs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.bs.dto.BsDTO;

@Mapper
public interface BsDAO {
	public void createBd(BsDTO bsDTO);
	public List<BsDTO> getBdList();
	public BsDTO getBdDetail(long bdId);
	public void updateReadCnt(long bdId);
	public void updateBd(BsDTO bsDTO);
	public void deleteBd(long bdId);	
}
