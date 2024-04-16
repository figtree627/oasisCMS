package com.application.oasisCMS.author.service;

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

import com.application.oasisCMS.author.dao.AuthorDAO;
import com.application.oasisCMS.author.dto.AuthorDTO;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Value("${file.repo.path.author}")
	// @Value("${file.repo.path}+author/") 이렇게 써도 되나?
    private String fileRepositoryPath;
	
	@Autowired 
	private AuthorDAO authorDAO;
	
	private static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
	
	@Override
	public void createBd(MultipartFile uploadImg, AuthorDTO authorDTO) throws IllegalStateException, IOException{
		System.out.println("[서비스] 저자 등록 도착!");
		
		if(uploadImg.isEmpty()) {
			authorDTO.setAuthorImg(fileRepositoryPath + "default.jpg");
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			authorDTO.setAuthorImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			authorDTO.setAuthorUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"author/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 저자 등록 내용 : " + authorDTO);
		
		 authorDAO.createBd(authorDTO);
	}
	
	
	@Override
	public List<AuthorDTO> getBdList() {

		List<AuthorDTO> bdList = authorDAO.getBdList();
		for(AuthorDTO dto : bdList ) {
			System.out.println("[서비스] 저자 리스트: " + dto);
			
		}
		
		return bdList;
	}

	@Override
	public AuthorDTO getBdDetail(long bdId) {
		System.out.println("[서비스] 저자 상세 : " + authorDAO.getBdDetail(bdId));
		authorDAO.updateReadCnt(bdId);
		return authorDAO.getBdDetail(bdId);
	}

	@Override
	public void updateBd(MultipartFile uploadImg, AuthorDTO authorDTO) throws IllegalStateException, IOException {
		System.out.println("[서비스] 저자 등록");
		
		if(uploadImg.isEmpty()) {
			authorDTO.setAuthorImg(fileRepositoryPath + authorDTO.getAuthorUUID());
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			authorDTO.setAuthorImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			authorDTO.setAuthorUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"author/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 저자 수정-: " + authorDTO);
		
		 authorDAO.updateBd(authorDTO);
	}
	
	@Override
	public void deleteBd(long bdId) {
		System.out.println("[서비스] 저자 삭제");

		authorDAO.deleteBd(bdId);
	}

}