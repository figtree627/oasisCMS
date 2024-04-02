package com.application.oasisCMS.memorial.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemorialDTO {
	private long mBdId;
	private String mBdTitle;
	private String mBdContent;
	private long mBdReadCnt;
	private Date mBdCreateDt;
	
}
