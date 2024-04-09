package com.application.oasisCMS.repl.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.oasisCMS.bd.service.BdService;
import com.application.oasisCMS.member.service.MemberService;
import com.application.oasisCMS.repl.dto.ReplDTO;
import com.application.oasisCMS.repl.service.ReplService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/repl")
public class ReplController {

	@Autowired
	private ReplService replService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BdService bdService;
	
	@GetMapping("/createRepl")
	public String createRepl(Model model, @RequestParam("bdId") long bdId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		System.out.println("리플 겟 컨트롤러 도착");
		model.addAttribute("bdId" , bdId);
		model.addAttribute("memberId", (String)session.getAttribute("memberId"));
		return "repl/createRepl";
	}
	
	@PostMapping("/createRepl")
	public String createRepl(@RequestParam Map<String,Object> createReplMap){
		System.out.println("리플생성 포스트 컨트롤러 도착");
		replService.createRepl(createReplMap);
		return "redirect:/bd/bdDetail?bdId=" + createReplMap.get("bdId");
	}
	
	
	// 같은 유저 작성 댓글인지 판단 > 수정 누르기 > 
	@GetMapping("/updateRepl")
	public String updateRepl(Model model , @RequestParam("replId") long replId){
		System.out.println("리플수정 겟 컨트롤러 도착");
		model.addAttribute("replDTO" , replService.getReplDetail(replId));
		return "repl/updateRepl";
		
	}
	
	@PostMapping("/updateRepl")
	public String updateRepl(@ModelAttribute ReplDTO replDTO){
		System.out.println("리플수정 포스트 컨트롤러 도착");
		replService.updateRepl(replDTO);
		
		return "redirect:/bd/bdDetail?bdId=" + replDTO.getBdId();
		
	}
	
	
	@GetMapping("/deleteRepl")
	public String deleteRepl(Model model , @RequestParam("replId") long replId) {
		
		System.out.println("리플삭제 컨트롤러 겟 도착");
		replService.deleteRepl(replId);
		
		return null;
	}
	
	
	
}
