package com.application.oasisCMS.member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dto.MemberDTO;
import com.application.oasisCMS.member.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Value("${file.repo.path}")
    private String fileRepositoryPath;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping
	public String test() {
		return "/main";
	}
	
	@GetMapping("/registerMember")
	public String registerMember() {
		return "/member/registerMember";
	}
	
	@PostMapping("/registerMember")
	public String registerMember(@RequestParam("uploadProfileImg") MultipartFile uploadProfileImg, 
								@ModelAttribute MemberDTO dto) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfileImg, dto);
		return "redirect:main";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
}
