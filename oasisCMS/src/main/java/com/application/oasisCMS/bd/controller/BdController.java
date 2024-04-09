package com.application.oasisCMS.bd.controller;

import java.util.List;
import java.util.Map;

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
import com.application.oasisCMS.repl.service.ReplService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bd")
public class BdController {
	
	@Autowired
	private BdService bdService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReplService replService;
	
	// ashion의 블로그를 적용할 예정
	@GetMapping
	public String a() {
		return "redirect:/bd/bdList";
	}
	
	@GetMapping("/")
	public String b() {
		return "redirect:/bd/bdList";
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		return "redirect:/bd/bdList";
	}
	
	// 완료
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("보드리스트 컨트롤러-겟 도착");
		List<Map<String,Object>> bdList = bdService.getBdList();
		for(Map<String,Object> map : bdList) {
			System.out.println("bdDTO :" + map);
		}
		model.addAttribute("bdList" ,bdList );
		return "bd/bdList";
			
		}
	// 완료
	@GetMapping("/createBd")
	public String createBd(Model model, HttpServletRequest req) {
		System.out.println("글쓰기 컨트롤러 - 겟 도착");
		// 세션 id에 where절에 넣어서 화면에 가져와라.
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return "bd/createBd";
	}
	
	// 완료 
	@PostMapping("/createBd") 
	public String createBd(@ModelAttribute BdDTO bdDTO) {
		
		// 단위테스트
		System.out.println("글쓰기 컨트롤러-포스트 도착");
		System.out.println(bdDTO);
		bdService.createBd(bdDTO);
		
		return "redirect:/bd/bdList";
	}
	
	// 완료
	@GetMapping("/bdDetail")
	public String bdDetail(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		// 단위테스트
		System.out.println(bdService.getBdDetail(bdId));
		
		Map<String,Object> bdDetailMap = bdService.getBdDetail(bdId);
			// System.out.println("bdDTO :" + bdDetailMap);
		model.addAttribute("bdDTO" , bdDetailMap );
		
//		System.out.println("getReplList : " + replService.getReplList(bdId));
//		System.out.println("getReplCnt : " + replService.getReplCnt(bdId));
		
		List<Map<String,Object>> replLIst = replService.getReplList(bdId);
		model.addAttribute("replList" , replLIst);
		model.addAttribute("replLIst" , replService.getReplCnt(bdId));
		
		return "bd/bdDetail";
	}
	
//	수정 > 확인창 > Y -> updateBd로 
//					N -> 그대로
// 	삭제 > 확인창 > Y -> deleteBd로
//					N -> 그대로 
	
	@GetMapping("/updateBd")
	public String updateBd(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
		System.out.println("get 업뎃보드도착 ");
		HttpSession session = req.getSession();
		model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		model.addAttribute("bdDTO", bdService.getBdDetail(bdId));
		return "bd/updateBd";
	}
	
	@PostMapping("/updateBd")
	@ResponseBody
	public String updateBd(@ModelAttribute BdDTO bdDTO) {
		bdService.updateBd(bdDTO);
		String jsScript = """
				<script>
					alert('수정 됐습니다.');
					location.href = '/bd/bdList';
				</script>
				""";
		return jsScript;
	} 
	
	@GetMapping("/deleteBd")
	public String deleteBd(@RequestParam("bdId") long bdId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		System.out.println("get delete보드도착 ");
		bdService.deleteBd(bdId);
		return "redirect:/bd/bdList";
	}
	
}
