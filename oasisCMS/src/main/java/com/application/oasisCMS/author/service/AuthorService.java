package com.application.oasisCMS.author.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.author.dto.AuthorDTO;

public interface AuthorService {
	public void createBd(MultipartFile uploadImg, AuthorDTO authorDTO) throws IllegalStateException, IOException;
	public List<AuthorDTO> getBdList();
	
	public AuthorDTO getBdDetail(long bdId);
	public void updateBd(MultipartFile uploadImg, AuthorDTO authorDTO) throws IllegalStateException, IOException;
	public void deleteBd(long bdId);
}
