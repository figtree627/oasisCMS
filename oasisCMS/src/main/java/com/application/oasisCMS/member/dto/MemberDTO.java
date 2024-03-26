package com.application.oasisCMS.member.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberDTO {
	String memberId;
	String pw;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date crateDt;
	String memberNm;
	String memberNick;
	String memberProfile_img;
	String memberProfile_UUID;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	String birthDt;
	char sex;
	String hp;
	String email;
	String zipcode;
	String roadAddress;
	String jibunAddress;
	String namujiAddress;
	String etc;
	char activeYn ;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date inactiveDt;
	char loginYn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date loginDt;
}
	