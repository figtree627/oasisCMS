package com.application.oasisCMS.member.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberDTO {
	String memberId;
	String passwd;
	Date joinDt;
	String memberNm;
	String memberNick;
	String profileImg;
	String profileUUID;
	char sex;
	String hp;
	String email;
	String zipcode;
	String roadAddress;
	String jibunAddress;
	String namujiAddress;
	String etc;
	char activeYn ;
	Date inactiveDt;
	char loginYn;
	Date loginDt;
}
	