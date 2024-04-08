package com.application.oasisCMS.bd.service;

import java.util.List;
import java.util.Map;

import com.application.oasisCMS.bd.dto.BdDTO;


public interface BdService {
	public void createBd(BdDTO bdDTO);
	public List<Map<String,Object>> getBdList();
	public Map<String,Object> getBdDetail(long bdId);
	public void updateBd(BdDTO bdDTO);
	public void deleteBd(long bdId);
}
