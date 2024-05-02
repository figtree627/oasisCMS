package com.application.oasisCMS.bs.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BsDTO {
	private long bdId;
	private String title;
	private String bsImg;
	private String bsUUID;
	private String content;
	private long readCnt;
	private int bsCategory1; // 0:월, 6:일
	private String bsCategory2;
	private String bsCategory3;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDt;
	
}
