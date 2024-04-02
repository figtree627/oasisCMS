package com.application.oasisCMS.bd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.application.oasisCMS.bd.dao.BdDAO;
import com.application.oasisCMS.bd.dto.BdDTO;


@Service
public class BdServiceImpl implements BdService {

	@Autowired
	private BdDAO bdDAO;

//	@Autowired
//	private PasswordEncoder passwordEncoder; 
	
	@Override
	public void createBd(BdDTO bdDTO) {
//		bdDTO.setPasswd(passwordEncoder.encode(bdDTO.getPasswd()));
		bdDAO.createBd(bdDTO);
	}

	@Override
	public List<BdDTO> getBdList() {
		return bdDAO.getBdList();
	}

	@Override
	public BdDTO getBdDetail(long bdId) {
		bdDAO.updateReadCnt(bdId);
		return bdDAO.getBdDetail(bdId);
	}

	// #################수정 필요
	@Override
	public boolean checkAuthorized(BdDTO bdDTO) {
		boolean isAuthorizedUser = false;
		// String encodedPassword = bdDAO.getEncodePasswd(bdDTO.getBdId());
		boolean isMatched = true; // passwordEncoder.matches(bdDTO.getPasswd(), encodedPassword);
		//if (passwordEncoder.matches(bdDTO.getPasswd() , bdDAO.getEncodePasswd(bdDTO.getBdId()))) {}
		if (isMatched) {
			isAuthorizedUser = true;
		}
		return isAuthorizedUser;
	}
	
	@Override
	public void updateBd(BdDTO bdDTO) {
		bdDAO.updateBd(bdDTO);
	}
	@Override
	public void deleteBd(long bdId) {
		bdDAO.deleteBd(bdId);
	}
}
