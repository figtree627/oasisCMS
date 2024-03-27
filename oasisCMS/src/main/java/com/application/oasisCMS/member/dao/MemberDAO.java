package com.application.oasisCMS.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.member.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
}
