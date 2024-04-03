package com.application.oasisCMS.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dto.MemberDTO;
import com.application.oasisCMS.member.service.MemberService;
import com.application.oasisCMS.member.service.MemberServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Value("${file.repo.path}")
    private String fileRepositoryPath;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@GetMapping("/main")
	public String main() {
//		단위테스트 
		System.out.println("index로 보내는 중");
		return "index";
	}
	
	@GetMapping("/registerMember")
	public String registerMember() {
		return "member/registerMember";
	}
	
	@PostMapping("/registerMember")
	public String registerMember(@RequestParam("profileImg") MultipartFile profileImg, 
								@ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(profileImg, memberDTO);
		return "redirect:main";
	} 
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
	
	@GetMapping("/loginMember")
	public String login() {
		return "member/loginMember";
	}

	@PostMapping("/loginMember")
	@ResponseBody
	public String loginMember(@RequestBody MemberDTO memberDTO , HttpServletRequest request) {
		
		String isValidMember = "n";
		if (memberService.login(memberDTO)) {
			System.out.println("컨트롤러 - 로그인 성공!");
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberDTO.getMemberId());
			
			isValidMember = "y";
			
		} 
		
		return isValidMember;
		
	}
}
