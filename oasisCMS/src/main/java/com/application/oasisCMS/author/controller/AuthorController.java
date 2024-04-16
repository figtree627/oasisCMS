package com.application.oasisCMS.author.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.author.dto.AuthorDTO;
import com.application.oasisCMS.author.service.AuthorService;
import com.application.oasisCMS.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/author")
public class AuthorController {
	
	@Value("${file.repo.path.author}")
    private String fileRepositoryPath;

	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/")
	public String main(Model model, HttpServletRequest req) {
			HttpSession session = req.getSession();
			model.addAttribute("memberId", (String)session.getAttribute("memberId"));
		return "redirect:/author/bdList";
	}
	
	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("저자-게시글 등록 컨트롤러-겟 도착");
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return "author/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute AuthorDTO authorDTO) 
							throws IllegalStateException, IOException{
//		public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute AuthorDTO authorDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("저자 등록 포스트 컨트롤러임");
		System.out.println("authorDTO : " + authorDTO);
		authorService.createBd(uploadImg, authorDTO);
		return "redirect:/author/bdList";
	}
	
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("저자-게시판리스트 컨트롤러-겟 도착");
		List<AuthorDTO> bdList = authorService.getBdList();
		for(AuthorDTO dto : bdList) {
			System.out.println("authorDTO :" + dto);
		}
		model.addAttribute("bdList" ,bdList );
		return "author/bdList";
	}
	
	// 상세조회
	@GetMapping("/bdDetail")
	public String bdDetail(Model model, @RequestParam("bdId") long bdId, HttpServletRequest req){
		HttpSession session = req.getSession();
		String memberId = (String) session.getAttribute("memberId");
		model.addAttribute("memberId", memberId);
		
		//단위테스트
		System.out.println(memberId);
		System.out.println("저자 보드 세부내용 겟 -   " + authorService.getBdDetail(bdId));
		
		AuthorDTO authorDTO = authorService.getBdDetail(bdId);
		model.addAttribute("authorDTO", authorDTO);
		
		return "author/bdDetail";
	}	
	
//	 썸네일기능 구현
	@GetMapping("/thumbnails")
	@ResponseBody
	public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
		// new UrlResource("file:" + 파일접근경로) 객체를 반환하여 이미지를 조회한다.
		return new UrlResource("file:" + fileRepositoryPath + fileName); // 이미지 경로 수정하여 사용
	}


	@GetMapping("/updateBd")
	public String updateBd(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
		System.out.println("저자 업뎃 수정 도착 ");
		HttpSession session = req.getSession();
		model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		model.addAttribute("authorDTO", authorService.getBdDetail(bdId));
		return "author/updateBd";
	}
	
	@PostMapping("/updateBd")
	@ResponseBody
	public String updateBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
			@ModelAttribute AuthorDTO authorDTO) 
			throws IllegalStateException, IOException{
		authorService.updateBd(uploadImg, authorDTO);
		String jsScript = """
				<script>
					location.href = '/author/bdList';
				</script>
				""";
		return jsScript;
	} 
	
	// 삭제
	@GetMapping("/deleteBd")
	public String deleteBd(@RequestParam("bdId") long bdId) {
		System.out.println("get delete보드도착 ");
		System.out.println(authorService.getBdList());
		authorService.deleteBd(bdId);
		return "redirect:/author/bdList";
	}


}
