package com.application.oasisCMS.memorial.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.memorial.dto.MemorialDTO;

public interface MemorialService {
	public void createBd(MultipartFile uploadImg, MemorialDTO memorialDTO) throws IllegalStateException, IOException;
	public List<MemorialDTO> getBdList();
	
	public MemorialDTO getBdDetail(long bdId);
	public void updateBd(MemorialDTO MemorialDTO);
	public void deleteBd(long bdId);
}
