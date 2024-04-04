package com.application.oasisCMS.bd.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.application.oasisCMS.bd.dto.BdDTO;

@Mapper
public interface BdDAO {
	public void createBd(BdDTO bdDTO);
	public List<Map<String,Object>> getBdList();
	public Map<String,Object> getBdDetail(long bdId);
	public void updateReadCnt(long bdId);
	public String getEncodePasswd(long bdId);
	public void updateBd(BdDTO bdDTO);
	public void deleteBd(long bdId);
}
