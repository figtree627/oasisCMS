package com.application.oasisCMS.memorial.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.memorial.dao.MemorialDAO;
import com.application.oasisCMS.memorial.dto.MemorialDTO;


@Service
public class MemorialServiceImpl implements MemorialService {

	@Value("${file.repo.path.memorial}")
	// @Value("${file.repo.path}+memorial/") 이렇게 써도 되나?
    private String fileRepositoryPath;
	
	@Autowired 
	private MemorialDAO memorialDAO;
	
	private static Logger logger = LoggerFactory.getLogger(MemorialServiceImpl.class);
	
	@Override
	public void createBd(MultipartFile uploadImg, MemorialDTO memorialDTO) throws IllegalStateException, IOException{
		System.out.println("기념관 등록 - 서비스 도착!");
		
		if(uploadImg.isEmpty()) {
			memorialDTO.setBdImg(fileRepositoryPath + "default.jpg");
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			memorialDTO.setBdImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			memorialDTO.setBdUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"memorial/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("멤버서비스 : " + memorialDTO);
		
		 memorialDAO.createBd(memorialDTO);
	}
	
	
	@Override
	public List<MemorialDTO> getBdList() {
		System.out.println("서비스 - 보드리스트 도착");
		
		return memorialDAO.getBdList();
	}

	@Override
	public MemorialDTO getBdDetail(long bdId) {
		memorialDAO.updateReadCnt(bdId);
		return memorialDAO.getBdDetail(bdId);
	}

	@Override
	public void updateBd(MemorialDTO memorialDTO) {
		memorialDAO.updateBd(memorialDTO);
	}
	@Override
	public void deleteBd(long bdId) {
		memorialDAO.deleteBd(bdId);
	}

}
