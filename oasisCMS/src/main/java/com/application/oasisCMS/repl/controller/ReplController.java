package com.application.oasisCMS.repl.controller;

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
		return "bd/createRepl";
	}
	
	@PostMapping("/createRepl")
	public String createRepl(@ModelAttribute ReplDTO replDTO){
		replService.createRepl(replDTO);
		System.out.println("리플 포스트 컨트롤러 도착");
		return "redirect:/bdAdvance/bdDetail?bdId=" + replDTO.getBdId();
	}
	// 같은 유저 작성 댓글인지 판단 > 수정 누르기 > 
	@GetMapping("/updateRepl")
	public String updateRepl(Model model , @RequestParam("replId") long replId){
		
		model.addAttribute("replyDTO" , replService.getReplDetail(replId));
		return "bdAdvance/reply/updateRepl";
		
	}
	
	@PostMapping("/updateRepl")
	public String updateRepl(@RequestBody ReplDTO replDTO){
		System.out.println("일단 포스트 리플 컨트롤러 도착");
		replService.updateRepl(replDTO);
		
		return "y";
		
	}
	
	
		
	
	/*
	@GetMapping("/deleteRepl")
	public String deleteRepl(Model model , @RequestParam("replyId") long replyId) {
		model.addAttribute("replyDTO" , replService.getReplDetail(replyId));
		return "bdAdvance/reply/deleteRepl";
	}
	
	
	@PostMapping("/deleteRepl")
	@ResponseBody
	public String deleteRepl(ReplDTO replyDTO) {
		
		String jsScript = "";
		if (replService.deleteRepl(replyDTO)) {
			jsScript += "<script>";
			jsScript += "location.href='/bd/bdDetail?bdId=" + replyDTO.getBdId() + "';";
			jsScript += "</script>";
		}
		else {
			jsScript = """
			   <script> 
				   alert('check your password');
				   history.go(-1);
			   </script>""";
		}
		
		return jsScript;
		
	}
	
	*/
}
