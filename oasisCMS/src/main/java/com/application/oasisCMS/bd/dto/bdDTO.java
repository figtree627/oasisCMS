package com.application.oasisCMS.bd.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class bdDTO {
	long bdId;
	String memberId;
	String bdTitle;
	String bdContent;
	String bdReadCnt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date createDt;
}
