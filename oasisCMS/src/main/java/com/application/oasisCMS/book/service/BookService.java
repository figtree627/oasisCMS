package com.application.oasisCMS.book.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.book.dto.BookDTO;

public interface BookService {
	public void createBd(MultipartFile uploadImg, BookDTO bookDTO) throws IllegalStateException, IOException;
	public List<BookDTO> getBdList();
	public BookDTO getBdDetail(long bdId);
	public void updateReadCnt(long bdId);
	public void updateBd(MultipartFile uploadImg, BookDTO bookDTO) throws IllegalStateException, IOException;
	public void deleteBd(long bdId);

}
