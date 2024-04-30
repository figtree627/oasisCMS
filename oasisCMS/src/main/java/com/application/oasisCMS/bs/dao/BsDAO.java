package com.application.oasisCMS.bs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.oasisCMS.bs.dto.BsDTO;

@Mapper
public interface BsDAO {
	public void createBd(BsDTO bsDTO);

	// 전체 방송 리스트 보여주기
	public List<BsDTO> getBdList();
	
	// 요일별 방송 리스트 보여주기
	public List<BsDTO> getBsByCategory(String category1);

	// 방송 세부내용 조회
	public BsDTO getBdDetail(long bdId);
	
	// 방송 조회수 
	public void updateReadCnt(long bdId);
	
	//  방송 수정
	public void updateBd(BsDTO bsDTO);
	
	// 방송 삭제
	public void deleteBd(long bdId);	
}
