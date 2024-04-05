package com.application.oasisCMS.member.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
	public void createMember(MultipartFile a, MemberDTO memberDTO) throws IllegalStateException, IOException  {
		
		if(a.isEmpty()) {
			memberDTO.setProfileImg(fileRepositoryPath + "default.jpg");
//			memberDTO.setProfileUUID(fileRepositoryPath+UUID.randomUUID()+".jpg");
		} else if (!a.isEmpty()) {
			String originalFilename = a.getOriginalFilename();
			
//			System.out.println("originalFilename : " + originalFilename);
			
			memberDTO.setProfileImg(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setProfileUUID(uploadFile);
			
			a.transferTo(new File(fileRepositoryPath + uploadFile));
			
		}
		//단위테스트
		System.out.println("멤버서비스 : " + memberDTO);
	
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd())); 
		memberDAO.createMember(memberDTO);
	}

	
	@Override
	public String checkValidId(String memberId)  {
		
		String isValidId = "y";
		if (memberDAO.checkValidId(memberId) != null) {
			isValidId = "n";
		}
		
		return isValidId;
		
	}
	
	
	@Override
	public boolean login(MemberDTO memberDTO)  {
		MemberDTO validateData = memberDAO.login(memberDTO.getMemberId());
		if (validateData != null) {
			String activeYnString = Character.toString(validateData.getActiveYn());
			if (passwordEncoder.matches(memberDTO.getPasswd() , validateData.getPasswd()) && !activeYnString.equals("n")) {
				System.out.println("서비스 - 로그인 성공!");
				return true;
			} 
		}
		return false;
	}
	
	@Override
	public MemberDTO getMemberDetail(String memberId)  {
		return memberDAO.getMemberDetail(memberId);
	}
	
	@Override
	public void updateMember(MultipartFile uploadProfile , MemberDTO memberDTO) throws IllegalStateException, IOException  {
		
		if (!uploadProfile.isEmpty()) {
			
			// 기존 프로파일 이미지 삭제
			new File(fileRepositoryPath + memberDTO.getProfileUUID()).delete();
			
			// 원본파일이름 저장
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileImg(originalFilename);
			
			// 확장자 찾기
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			// 새파일 = UUID + 확장자 처리 후 dto의 UUID를 저장
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setProfileUUID(uploadFile);
			
			// 
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
			
		}
		
		memberDAO.updateMember(memberDTO);
	
	}
	
	
	@Override
	public void updateInactiveMember(String memberId)  {
		memberDAO.updateInactiveMember(memberId);
	}

	
	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void getTodayNewMemberCnt()  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		logger.info("(" + today + ") 신규회원수 : " + memberDAO.getTodayNewMemberCnt(today));
	}
	
	
	@Override
	@Scheduled(cron="59 59 23 * * *")
	public void deleteMemberScheduler()  {
		List<MemberDTO> deleteMemberList = memberDAO.getInActiveMemberList();
		if (!deleteMemberList.isEmpty()) {
			for (MemberDTO memberDTO : deleteMemberList) {
				new File(fileRepositoryPath + memberDTO.getProfileUUID()).delete();
				memberDAO.deleteMember(memberDTO.getMemberId());
			}
		}
	}
	
}
