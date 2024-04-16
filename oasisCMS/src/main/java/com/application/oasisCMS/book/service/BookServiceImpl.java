package com.application.oasisCMS.book.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.book.dto.BookDTO;
import com.application.oasisCMS.book.dao.BookDAO;
import com.application.oasisCMS.book.dto.BookDTO;
import com.application.oasisCMS.memorial.service.MemorialServiceImpl;

@Service
public class BookServiceImpl implements BookService {
	@Value("${file.repo.path.book}")
    private String fileRepositoryPath;
	
	@Autowired 
	private BookDAO bookDAO;
	
	private static Logger logger = LoggerFactory.getLogger(MemorialServiceImpl.class);
	
	@Override
	public void createBd(MultipartFile uploadImg, BookDTO bookDTO) throws IllegalStateException, IOException {
System.out.println("[서비스] 도서 등록 도착!");
		
		if(uploadImg.isEmpty()) {
			bookDTO.setBookImg(fileRepositoryPath + "default.jpg");
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			bookDTO.setBookImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			bookDTO.setBookUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"book/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 저자 등록 내용 : " + bookDTO);
		
		 bookDAO.createBd(bookDTO);
	}
	

	@Override
	public List<BookDTO> getBdList() {
		List<BookDTO> bdList = bookDAO.getBdList();
		for(BookDTO dto : bdList ) {
			System.out.println("[서비스] 도서 리스트: " + dto);
		}
		return bdList;
		
	}

	@Override
	public BookDTO getBdDetail(long bdId) {
		System.out.println("[서비스] 저자 상세 : " + bookDAO.getBdDetail(bdId));
		bookDAO.updateReadCnt(bdId);
		return bookDAO.getBdDetail(bdId);
	}

	@Override
	public void updateReadCnt(long bdId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBd(MultipartFile uploadImg, BookDTO bookDTO) throws IllegalStateException, IOException {
		System.out.println("[서비스] 저자 등록");
		
		if(uploadImg.isEmpty()) {
			bookDTO.setBookImg(fileRepositoryPath + bookDTO.getBookUUID());
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			bookDTO.setBookImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			bookDTO.setBookUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"book/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 저자 수정-: " + bookDTO);
		
		 bookDAO.updateBd(bookDTO);
	}
	
	@Override
	public void deleteBd(long bdId) {
		System.out.println("[서비스] 저자 삭제");

		bookDAO.deleteBd(bdId);
	}

}