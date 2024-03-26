package com.application.oasisCMS.bs.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BsDTO {
	long bsId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date createDt;
	String bsTitle;
	String bsImg;
	String bsUUID;
	long bsReadCnt;
	String bsCategory1;
	String bsCategory2;
	String bsCategory3;
	
}
