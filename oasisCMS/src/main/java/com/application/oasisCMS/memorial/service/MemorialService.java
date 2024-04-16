package com.application.oasisCMS.memorial.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.memorial.dto.MemorialDTO;

public interface MemorialService {
	public void createBd(MultipartFile uploadImg, MemorialDTO memorialDTO) throws IllegalStateException, IOException;
	public List<MemorialDTO> getBdList1();
	public List<MemorialDTO> getBdList2();
	public List<MemorialDTO> getBdList3();
	
	public MemorialDTO getBdDetail(long bdId);
	public void updateBd(MultipartFile uploadImg, MemorialDTO memorialDTO) throws IllegalStateException, IOException;
	public void deleteBd(long bdId);
}
