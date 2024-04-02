package com.application.oasisCMS.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.member.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public MemberDTO login(String memberId);
	public MemberDTO getMemberDetail(String memberId);
	public void updateMember(MemberDTO memberDTO);
	public void updateInactiveMember(String memberId);
	
	public List<MemberDTO> getMemberList();
	public int getTodayNewMemberCnt(String today);
	public List<MemberDTO> getInActiveMemberList();
	public void deleteMember(String memberId);
}
