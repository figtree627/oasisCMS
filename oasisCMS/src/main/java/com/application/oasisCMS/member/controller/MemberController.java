package com.application.oasisCMS.member.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@Value("${file.repo.path.member}")
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
	public String registerMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session !=null) {
			session.invalidate();
		}
		return "member/registerMember";
	}
	
	@PostMapping("/registerMember")
	public String registerMember(@RequestParam("a") MultipartFile a, 
								@ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		System.out.println("멤버컨트롤러 포스트"+ memberDTO);
		memberService.createMember(a, memberDTO);
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
	
	@GetMapping("/logoutMember")
	public String logoutMember(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:main";
		
	}
	
	@GetMapping("/memberUUID")
    @ResponseBody
    public Resource thumbnails(@RequestParam("memberId") String memberId) throws IOException{
		System.out.println("[컨트롤러] 멤버 - memberUUID 겟매핑 도착");
        String fileName = memberService.getMemberDetail(memberId).getProfileUUID();
		System.out.println(fileName);
		return new UrlResource("file:" + fileRepositoryPath + fileName);
    }
	
	
	@GetMapping("/updateMember")
	public String updateMember(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		MemberDTO memberDTO = memberService.getMemberDetail((String)session.getAttribute("memberId"));
		System.out.println("[컨트롤러] 멤버 겟 :" + memberDTO );
		
		model.addAttribute("memberDTO", memberDTO);
		return "member/updateMember";
	}
	
	@PostMapping("/updateMember")
	public String updateMember(@RequestParam("a") MultipartFile a, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		System.out.println("[컨트롤러] 멤버 수정 - 포스트: " + memberDTO);
		memberService.updateMember(a, memberDTO);
		return "redirect:main";
	
	}
}
