package com.application.oasisCMS.author.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AuthorDTO {
	private long authorId;
	private long bdId;
	private String title;
	private long bookId;
	private String authorNm;
	private String content;
	private String authorImg;
	private String authorUUID;
	private long readCnt;
	private Date createDt;
}
