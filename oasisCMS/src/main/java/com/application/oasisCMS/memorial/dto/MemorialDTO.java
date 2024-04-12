package com.application.oasisCMS.memorial.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemorialDTO {
	private long BdId;
	private String Title;
	private String Content;
	private String bdImg;
	private String bdUUID;
	private long ReadCnt;
	private Date CreateDt;
	
}
