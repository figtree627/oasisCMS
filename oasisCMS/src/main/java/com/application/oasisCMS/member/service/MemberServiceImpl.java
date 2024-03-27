package com.application.oasisCMS.member.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dao.MemberDAO;
import com.application.oasisCMS.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Value("${file.repo.path}")
    private String fileRepositoryPath;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Override
	public void createMember(MultipartFile uploadProfileImg, MemberDTO memberDTO)
			throws IllegalStateException, IOException {
		if(!uploadProfileImg.isEmpty()) {
			
			String originalFileName = uploadProfileImg.getOriginalFilename();
			memberDTO.setMemberProfileImg(originalFileName);
			
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setMemberProfileUUID(uploadFile);
			uploadProfileImg.transferTo(new File(fileRepositoryPath + uploadFile));
		}
		
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDAO.createMember(memberDTO);
	}
	
	@Override
	public String checkValidId(String memberId) {
		String isValidId = "y";
		if(memberDAO.checkValidId(memberId)!= null) {
			isValidId = "n";
		}
		return isValidId;
	}
	
}
