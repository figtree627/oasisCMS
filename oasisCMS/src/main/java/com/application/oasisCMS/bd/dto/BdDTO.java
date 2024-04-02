package com.application.oasisCMS.bd.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BdDTO {
	long bdId;
	String memberId;
	String title;
	String content;
	String readCnt;
	Date createDt;
}
