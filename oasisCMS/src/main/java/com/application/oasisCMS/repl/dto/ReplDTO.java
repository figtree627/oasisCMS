package com.application.oasisCMS.repl.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplDTO {
	
	private long replId;
	private long bdId;
	private String memberId;
	private String memberNick;
	private String content;
	private Date createDt;
	
}
