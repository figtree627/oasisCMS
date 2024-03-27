package com.application.oasisCMS.member.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dto.MemberDTO;

public interface MemberService {
	public void createMember(MultipartFile uploadProfileImg, MemberDTO memberDTO) throws IllegalStateException, IOException;
	public String checkValidId(String memberId);
}
