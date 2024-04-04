package com.application.oasisCMS.bd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.oasisCMS.bd.dto.BdDTO;
import com.application.oasisCMS.bd.service.BdService;
import com.application.oasisCMS.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bd")
public class BdController {
	
	@Autowired
	private BdService bdService;

	@Autowired
	private MemberService memberService;
	
	
	// ashion의 블로그를 적용할 예정
	@GetMapping("/")
	public String a() {
		return "bd/bdList";
	}
	
	@GetMapping("/main")
	public String main() {
		return "bd/bdList";
	}
	
	@GetMapping("/createBd")
	public String createBd(Model model, HttpServletRequest req) {
		System.out.println("글쓰기 컨트롤러 - 겟 도착");
		// 세션 id에 where절에 넣어서 화면에 가져와라.
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return "bd/createBd";
	}
	
	@PostMapping("/createBd") 
	public String createBd(@ModelAttribute BdDTO bdDTO) {
		// 단위테스트
		System.out.println("글쓰기 컨트롤러-포스트 도착");
		System.out.println(bdDTO);
		
		bdService.createBd(bdDTO);
		
		return "bd/bdList";
	}
	
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("보드리스트 컨트롤러-겟 도착");
		List<BdDTO> bdList = bdService.getBdList();
		for(BdDTO bdDTO: bdList) {
			System.out.println("bdDTO :" + bdDTO);
		}
		model.addAttribute("bdList" ,bdList );
		return "bd/bdList";
	}
	
	
	@GetMapping("/bdDetail")
	public String bdDetail(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		// 단위테스트
		//System.out.println(bdService.getBdDetail(bdId));
		model.addAttribute("bdDTO", bdService.getBdDetail(bdId));
		return "bd/bdDetail";
	}
	
	@GetMapping("/authentication")
	public String authentication(Model model, @RequestParam("bdId") long bdId , 
								 @RequestParam("menu") String menu) {
		model.addAttribute("bdDTO" , bdService.getBdDetail(bdId));
		model.addAttribute("menu" , menu);
		return "bd/authentication";
	}
	
	@PostMapping("/authentication")
	@ResponseBody
	public String authentication(@ModelAttribute BdDTO bdDTO, 
								 @RequestParam("menu") String menu) {
		
		String jsScript = "";
		if (bdService.checkAuthorized(bdDTO)) { // 인증성공
			if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript += "location.href='/bd/updateBd?bdId="+ bdDTO.getBdId()+ "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
				jsScript = "<script>";
				jsScript += "location.href='/bd/deleteBd?bdId="+ bdDTO.getBdId()+ "';";
				jsScript += "</script>";
			}
		}
		else {  // 인증 실패
			jsScript = """
				<script>
					alert('패스워드를 확인하세요.');
					history.go(-1);
				</script>
				""";
		}
		return jsScript;
	}
	
	@GetMapping("/updateBd")
	public String updateBd(Model model , @RequestParam("bdId") long bdId) {
		model.addAttribute("bdDTO", bdService.getBdDetail(bdId));
		return "bd/updateBd";
	}
	
	
	@PostMapping("/updateBd")
	@ResponseBody
	public String updateBd(@ModelAttribute BdDTO bdDTO) {
		
		bdService.updateBd(bdDTO);
		
		String jsScript = """
				<script>
					alert('수정 되었습니다.');
					location.href = '/bd/bdList';
				</script>
				""";
		
		return jsScript;
		
	} 
	
	
	@GetMapping("/deleteBd")
	public String deleteBd(Model model , @RequestParam("bdId") long bdId) {
		model.addAttribute("bdId", bdId);
		return "bd/deleteBd";
	}
	
	
	@PostMapping("/deleteBd")
	@ResponseBody
	public String deleteBd(@RequestParam("bdId") long bdId) {
		
		bdService.deleteBd(bdId);
		
		String jsScript = """
				<script>
					alert('삭제 되었습니다.');
					location.href = '/bd/bdList';
				</script>
				""";
		
		return jsScript;
	}
	
}
