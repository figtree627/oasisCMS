package com.application.oasisCMS.bs.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BsDTO {
	long bdId;
	String title;
	String bsImg;
	String bsUUID;
	String content;
	long readCnt;
	String bsCategory1;
	String bsCategory2;
	String bsCategory3;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date createDt;
	
}
