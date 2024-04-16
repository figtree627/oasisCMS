package com.application.oasisCMS.book.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BookDTO {
	private long bookId;
	private long authorId;
	private String bookNm;
	private String bookImg;
	private String bookUUID;
	private long publisherId ;
	private String publisherNm;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pubDt;
	private String introOneline;
	private String introDetail;
	private long readCnt;
}
