package com.application.oasisCMS.member.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dto.MemberDTO;

public interface MemberService {
	// 8개 - 멤버 만들기, 아이디중복체크, 로그인, 세부내용, 멤버수정, 휴면계정으로수정, 오늘자 신규회원숫자, 탈퇴

	// 멤버 만들기
	public void createMember(MultipartFile uploadProfileImg , MemberDTO memberDTO) throws IllegalStateException, IOException;
	
	// 아이디 중복체크
	public String checkValidId(String memberId);
	
	// 로그인 
	public boolean login(MemberDTO memberDTO) ;
	
	// 멤버 세부내용
	public MemberDTO getMemberDetail(String memberId) ;
	
	// 멤버 수정
	public void updateMember(MultipartFile uploadProfile , MemberDTO memberDTO) 
			throws IllegalStateException, IOException;
	
	// 휴면계정 수정 
	public void updateInactiveMember(String memberId) ;
	
	// 오늘자 신규회원 카운트
	public void getTodayNewMemberCnt();
	
	// 탈퇴처리스케쥴러
	public void deleteMemberScheduler();
}
