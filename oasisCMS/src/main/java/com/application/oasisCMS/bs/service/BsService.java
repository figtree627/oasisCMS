package com.application.oasisCMS.bs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.bs.dto.BsDTO;

public interface BsService {
	public void createBd(MultipartFile uploadImg, BsDTO bsDTO) throws IllegalStateException, IOException;
	public List<BsDTO> getBdList();
	public List<BsDTO> getBsByCategory(String category1);
	public BsDTO getBdDetail(long bdId);
	public void updateBd(MultipartFile uploadImg, BsDTO bsDTO) throws IllegalStateException, IOException;
	public void deleteBd(long bdId);
	public List<BsDTO> getBdListByCategory(int category);
	public List<BsDTO> getAllBdList();
}
