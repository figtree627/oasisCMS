package com.application.oasisCMS.memorial.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.memorial.dto.MemorialDTO;

@Mapper
public interface MemorialDAO {	
		public void createBd(MemorialDTO memorialDTO);
		public List<MemorialDTO> getBdList();
		public MemorialDTO getBdDetail(long bdId);
		public void updateReadCnt(long bdId);
		public void updateBd(MemorialDTO bdDTO);
		public void deleteBd(long bdId);
	}
