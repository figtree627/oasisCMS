package com.application.oasisCMS.book.dao;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.book.dto.BookDTO;

@Mapper
public interface BookDAO {
	public void createBd(BookDTO bookDTO);
	public List<BookDTO> getBdList();
	public BookDTO getBdDetail(long bdId);
	public void updateReadCnt(long bdId);
	public void updateBd(BookDTO bdDTO);
	public void deleteBd(long bdId);
}
