package com.application.oasisCMS.memorial.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.member.dto.MemberDTO;
import com.application.oasisCMS.member.service.MemberService;
import com.application.oasisCMS.memorial.dto.MemorialDTO;
import com.application.oasisCMS.memorial.service.MemorialService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/memorial")
public class MemorialController {

	@Value("${file.repo.path.memorial}")
    private String fileRepositoryPath;
	
	@Autowired
	private MemorialService memorialService;
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/main")
	public String main() {
		return "redirect:/memorial/bdList";
	}
	
	@GetMapping("/")
	public String m() {
		return "redirect:/memorial/bdList";
	}
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	

	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("기념관-게시판리스트 컨트롤러-겟 도착");
		List<MemorialDTO> bdList = memorialService.getBdList();
		for(MemorialDTO dto : bdList) {
			System.out.println("memorialDTO :" + dto);
		}
		model.addAttribute("bdList" ,bdList );
		return "memorial/bdList";
	}
	
	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("기념관-게시글 등록 컨트롤러-겟 도착");
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return "memorial/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute MemorialDTO memorialDTO) 
							throws IllegalStateException, IOException{
//		public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute MemorialDTO memorialDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("기념관 등록 포스트 컨트롤러임");
		System.out.println("memorialDTO : " + memorialDTO);
		memorialService.createBd(uploadImg, memorialDTO);
		return "redirect:/memorial/bdList";
	}
		
		@GetMapping("/bdDetail")
		public String bdDetail(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
	        
			HttpSession session = req.getSession();
			String memberId = (String)session.getAttribute("memberId");
	        model.addAttribute("memberId", memberId);

			// 단위테스트
			System.out.println("기념관보드세부내용 겟 - " + memorialService.getBdDetail(bdId));
			
			MemorialDTO momorialDTO = memorialService.getBdDetail(bdId);
			model.addAttribute("memorialDTO" , momorialDTO);

			return "memorial/bdDetail";
		}
		
		// 썸네일기능 구현 예시 : 원래는 썸내일을 build.gradle에 구현을 했어야 했다. 지금은 그냥 하면 된다. 
		@GetMapping("/thumbnails")
		@ResponseBody
		public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
			// new UrlResource("file:" + 파일접근경로) 객체를 반환하여 이미지를 조회한다.
			return new UrlResource("file:" + fileRepositoryPath + fileName); // 이미지 경로 수정하여 사용
		}
		
		
		
		@GetMapping("/updateBd")
		public String updateBd(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
			System.out.println("get 업뎃보드도착 ");
			HttpSession session = req.getSession();
			model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
			model.addAttribute("memorialDTO", memorialService.getBdDetail(bdId));
			return "memorial/updateBd";
		}
		
		@PostMapping("/updateBd")
		@ResponseBody
		public String updateBd(@ModelAttribute MemorialDTO memorialDTO) {
			memorialService.updateBd(memorialDTO);
			String jsScript = """
					<script>
						alert('수정 됐습니다.');
						location.href = '/memorial/bdList';
					</script>
					""";
			return jsScript;
		} 
		
		@GetMapping("/deleteBd")
		public String deleteBd(@RequestParam("bdId") long bdId, HttpServletRequest req) {
			HttpSession session = req.getSession();
			System.out.println("get delete보드도착 ");
			memorialService.deleteBd(bdId);
			return "redirect:/memorial/bdList";
		}
	
		
}
