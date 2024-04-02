package com.application.oasisCMS.bd.service;

import java.util.List;

import com.application.oasisCMS.bd.dto.BdDTO;


public interface BdService {
	public void createBd(BdDTO bdDTO);
	public List<BdDTO> getBdList();
	public BdDTO getBdDetail(long bdId);
	public void updateBd(BdDTO bdDTO);
	public void deleteBd(long bdId);

	public boolean checkAuthorized(BdDTO bdDTO);
}
