package com.application.oasisCMS.bs.service;

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

import com.application.oasisCMS.bs.dao.BsDAO;
import com.application.oasisCMS.bs.dto.BsDTO;
import com.application.oasisCMS.bs.service.BsServiceImpl;

@Service
public class BsServiceImpl implements BsService {
	@Value("${file.repo.path.bs}")
    private String fileRepositoryPath;
	
	@Autowired 
	private BsDAO bsDAO;
	
	private static Logger logger = LoggerFactory.getLogger(BsServiceImpl.class);
	
	@Override
	public void createBd(MultipartFile uploadImg, BsDTO bsDTO) throws IllegalStateException, IOException{
		System.out.println("[서비스] 방송 등록 도착!");
		
		if(uploadImg.isEmpty()) {
			bsDTO.setBsImg(fileRepositoryPath + "default.jpg");
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			bsDTO.setBsImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			bsDTO.setBsUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"bs/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 방송 등록 내용 : " + bsDTO);
		
		 bsDAO.createBd(bsDTO);
	}
	
	
	@Override
	public List<BsDTO> getBdList() {

		List<BsDTO> bdList = bsDAO.getBdList();
		for(BsDTO dto : bdList ) {
			System.out.println("[서비스] 방송 리스트: " + dto);
			
		}
		
		return bdList;
	}

	@Override
	public BsDTO getBdDetail(long bdId) {
		System.out.println("[서비스] 방송 상세 : " + bsDAO.getBdDetail(bdId));
		bsDAO.updateReadCnt(bdId);
		return bsDAO.getBdDetail(bdId);
	}

	@Override
	public void updateBd(MultipartFile uploadImg, BsDTO bsDTO) throws IllegalStateException, IOException {
		System.out.println("[서비스] 방송 등록");
		
		if(uploadImg.isEmpty()) {
			bsDTO.setBsImg(fileRepositoryPath + bsDTO.getBsUUID());
			
		} else if (!uploadImg.isEmpty()) {
			String originalFilename = uploadImg.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);
			
			bsDTO.setBsImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			bsDTO.setBsUUID(uploadFile);
			
			uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
// 			uploadImg.transferTo(new File(fileRepositoryPath +"bs/"+ uploadFile));
			
		}
		//단위테스트
		System.out.println("[서비스] 방송 수정-: " + bsDTO);
		
		 bsDAO.updateBd(bsDTO);
	}
	
	@Override
	public void deleteBd(long bdId) {
		System.out.println("[서비스] 방송 삭제");

		bsDAO.deleteBd(bdId);
	}

}
